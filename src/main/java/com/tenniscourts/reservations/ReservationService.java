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
    //1. As a User I want to be able to book a reservation for one or more tennis court at a given date schedule
    public ReservationDTO bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
        return reservationMapper.map(reservationRepository.saveAndFlush(reservationMapper.map(createReservationRequestDTO)));
    }

    public ReservationDTO findReservation(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservationMapper::map).orElseThrow(() -> {
            return new EntityNotFoundException("Reservation not found.");
        });
    }
    //3. As a User I want to be able to cancel a reservation
    public ReservationDTO cancelReservation(Long reservationId) {
        return reservationMapper.map(this.cancel(reservationId));
    }

    private Reservation cancel(Long reservationId) {
        return reservationRepository.findById(reservationId).map(reservation -> {

            this.validateCancellation(reservation);

            BigDecimal refundValue = getRefundValue(reservation);
            return this.updateReservation(reservation, refundValue, ReservationStatus.CANCELLED);

        }).orElseThrow(() -> {
            return new EntityNotFoundException("Reservation not found.");
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
        long hours = ChronoUnit.HOURS.between(LocalDateTime.now(), reservation.getSchedule().getStartDateTime());

      /* 9. As a Tennis Court Admin, I want to keep 25% of the reservation fee if the User cancels or reschedules between 12:00
            and 23:59 hours in advance,
                50% between 2:00 and 11:59 in advance, and 75% between 0:01 and 2:00 in advance*/
        if (hours >= 24) {
            return reservation.getValue();
        } else if(hours >= 12) {
            return reservation.getValue().multiply(new BigDecimal(.25));
        } else if(hours >= 2) {
            return reservation.getValue().multiply(new BigDecimal(.50));
        } else if(hours > 0){
            return reservation.getValue().multiply(new BigDecimal(.75));
        }

        return BigDecimal.ZERO;
    }

    /*TODO: This method actually not fully working, find a way to fix the issue when it's throwing the error:
            "Cannot reschedule to the same slot.*/
   //4. As a User I want to be able to reschedule a reservation
    public ReservationDTO rescheduleReservation(Long previousReservationId, Long scheduleId) {
        Long previousScheduleId = findReservation(previousReservationId).getSchedule().getId();
        if (scheduleId.equals(previousScheduleId)) {
            throw new IllegalArgumentException("Cannot reschedule to the same slot.");
        }
        Reservation previousReservation = cancel(previousReservationId);
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
