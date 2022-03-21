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

    private final ReservationRepository reservationRepository;

    private final ReservationMapper reservationMapper;

    public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
        Reservation reservation = reservationMapper.map(createReservationRequestDTO);
        reservation.setReservationStatus(ReservationStatus.READY_TO_PLAY);
        reservation.setValue(new BigDecimal(10));
        return reservationMapper.map(reservationRepository.save(reservation));
    }

    public ReservationDTO findReservation(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservationMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Reservation not found.");
        });
    }

    public ReservationDTO cancelReservation(Long reservationId) {
        Reservation cancelledReservation = cancel(reservationId);
        return reservationMapper.map(this.updateReservation(cancelledReservation, ReservationStatus.CANCELLED));    
    }

    private Reservation cancel(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservation -> {

            this.validateCancellation(reservation);

            BigDecimal refundValue = getRefundValue(reservation);
            reservation.setRefundValue(refundValue);
            reservation.setValue(reservation.getValue().subtract(reservation.getRefundValue()));

            return this.updateReservation(reservation, ReservationStatus.CANCELLED);

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

        if (minutes >= 1440) {
            return reservation.getValue();
        }else if(minutes >= 720){
            BigDecimal percentageAmount = reservation.getValue().multiply(BigDecimal.valueOf((double)25/100));
            return reservation.getValue().subtract(percentageAmount);
        }else if(minutes >= 120 ){
            BigDecimal percentageAmount = reservation.getValue().multiply(BigDecimal.valueOf((double)50/100));
            return reservation.getValue().subtract(percentageAmount);
        }else if(minutes >= 1){
            BigDecimal percentageAmount = reservation.getValue().multiply(BigDecimal.valueOf((double)75/100));
            return reservation.getValue().subtract(percentageAmount);
        }

        return BigDecimal.ZERO;
    }

    /*TODO: This method actually not fully working, find a way to fix the issue when it's throwing the error:
            "Cannot reschedule to the same slot.*/
    public ReservationDTO rescheduleReservation(Long previousReservationId, Long scheduleId) {
        Reservation previousReservation = cancel(previousReservationId);

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
