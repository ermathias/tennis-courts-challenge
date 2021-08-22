package com.tenniscourts.reservations;

import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

@Service
@AllArgsConstructor
public class ReservationService {

    @AllArgsConstructor
    enum KeepRange {

        OVER_2400 (24 * 60, new BigDecimal(1)),
        BETWEEN_1200_2359 (12 * 60, new BigDecimal("0.75")),
        BETWEEN_0200_1159 (2 * 60, new BigDecimal("0.5")),
        BETWEEN_0001_0159 (0, new BigDecimal("0.25")),
        BELOW_ZERO (0, BigDecimal.ZERO),
        ;

        private final int startMinute;
        private final BigDecimal percentageToRefund;

    }

    private final ReservationRepository reservationRepository;

    private final ReservationMapper reservationMapper;

    public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
        Reservation reservation = reservationMapper.map(createReservationRequestDTO);
        reservation.setReservationStatus(ReservationStatus.READY_TO_PLAY);
        reservation.setValue(new BigDecimal("10.0"));
        return reservationMapper.map(reservationRepository.save(reservation));
    }

    public ReservationDTO findReservation(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservationMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found.");
        });
    }

    public ReservationDTO cancelReservation(Long reservationId) {
        Reservation reservationToCancel = reservationCanBeCanceled(reservationId);
        return reservationMapper.map(this.updateReservation(reservationToCancel, ReservationStatus.CANCELLED));
    }

    private Reservation reservationCanBeCanceled(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservation -> {

            this.validateCancellation(reservation);

            reservation.setRefundValue(getRefundValue(reservation));
            reservation.setValue(reservation.getValue().subtract(reservation.getRefundValue()));
            return reservation;

        }).orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found.");
        });
    }

    private Reservation updateReservation(Reservation reservation, ReservationStatus status) {
        reservation.setReservationStatus(status);

        return reservationRepository.save(reservation);
    }

    private void validateCancellation(Reservation reservation) {
        if (!ReservationStatus.READY_TO_PLAY.equals(reservation.getReservationStatus())) {
            throw new IllegalArgumentException("Cannot cancel/reschedule because it's not in ready to play status.");
        }

        if (reservation.getSchedule().getStartDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Can cancel/reschedule only future dates.");
        }
    }

    public BigDecimal getRefundValue(Reservation reservation) {
        long minutes = ChronoUnit.MINUTES.between(LocalDateTime.now(), reservation.getSchedule().getStartDateTime());

        KeepRange keepRangeSelected = Arrays.stream(KeepRange.values())
                .filter(keepRange -> minutes >= keepRange.startMinute)
                .findFirst()
                .orElse(KeepRange.BELOW_ZERO);

        return reservation.getValue().multiply(keepRangeSelected.percentageToRefund);

    }

    public ReservationDTO rescheduleReservation(Long previousReservationId, Long scheduleId) {
        Reservation previousReservation = reservationCanBeCanceled(previousReservationId);

        if (scheduleId.equals(previousReservation.getSchedule().getId())) {
            throw new IllegalArgumentException("Cannot reschedule to the same slot.");
        }

        this.updateReservation(previousReservation, ReservationStatus.RESCHEDULED);

        ReservationDTO newReservation = bookReservation(CreateReservationRequestDTO.builder()
                .guestId(previousReservation.getGuest().getId())
                .scheduleId(scheduleId)
                .build());
        newReservation.setPreviousReservation(reservationMapper.map(previousReservation));
        return newReservation;
    }
}
