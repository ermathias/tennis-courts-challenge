package com.tenniscourts.reservations;

import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@AllArgsConstructor
public class ReservationService {

    private final static BigDecimal DEPOSIT = new BigDecimal(10.0);

    private final static BigDecimal ONE_QUARTER = new BigDecimal(0.25);

    private final static BigDecimal HALF = new BigDecimal(0.50);

    private final static BigDecimal THREE_QUARTER = new BigDecimal(0.75);

    private final ReservationRepository reservationRepository;

    private final ReservationMapper reservationMapper;

    public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
        Reservation newReservation = reservationMapper.map(createReservationRequestDTO);
        newReservation.setValue(DEPOSIT);
        newReservation.setReservationStatus(ReservationStatus.READY_TO_PLAY);
        return reservationMapper.map(reservationRepository.save(newReservation));
    }

    public ReservationDTO findReservation(Long reservationId) throws Throwable{
        return reservationRepository.findById(reservationId).map(reservationMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found.");
        });
    }

    public ReservationDTO cancelReservation(Long reservationId) throws Throwable{
        return reservationMapper.map(this.cancel(reservationId));
    }

    private Reservation cancel(Long reservationId) throws Throwable{
        return reservationRepository.findById(reservationId).map(reservation -> {

            this.validateCancellation(reservation);

            BigDecimal refundValue = getRefundValue(reservation);
            return this.updateReservation(reservation, refundValue, ReservationStatus.CANCELLED);

        }).orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found.");
        });
    }

    private Reservation updateReservation(Reservation reservation, BigDecimal refundValue, ReservationStatus status) {
        reservation.setReservationStatus(status);
        reservation.setValue(reservation.getValue().subtract(refundValue));
        reservation.setRefundValue(refundValue);

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
        BigDecimal reservationValue = reservation.getValue();

        if (minutes >= 24 * 60) { //hours >= 24)
            return reservationValue;
        } else if (minutes >= 12 * 60) {  //hours >= 12 && hours < 24
            return reservationValue.multiply(THREE_QUARTER);
        } else if (minutes >= 2 * 60) { //hours >= 2 && hours < 12
            return reservationValue.multiply(HALF);
        } else if (minutes > 0) { //hours >0 && hours < 2
            return reservationValue.multiply(ONE_QUARTER);
        }

        return BigDecimal.ZERO;
    }

    /*TODO: This method actually not fully working, find a way to fix the issue when it's throwing the error:
            "Cannot reschedule to the same slot.*/
    public ReservationDTO rescheduleReservation(Long previousReservationId, Long scheduleId) throws Throwable{
        Reservation previousReservation = cancel(previousReservationId);

        if (scheduleId.equals(previousReservation.getSchedule().getId())) {
            throw new IllegalArgumentException("Cannot reschedule to the same slot.");
        }

        previousReservation.setReservationStatus(ReservationStatus.RESCHEDULED);
        reservationRepository.save(previousReservation);

        ReservationDTO newReservation = bookReservation(CreateReservationRequestDTO.builder()
                .guestId(previousReservation.getGuest().getId())
                .scheduleId(scheduleId)
                .build());
        newReservation.setPreviousReservation(reservationMapper.map(previousReservation));
        return newReservation;
    }
}
