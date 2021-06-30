package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reservation")

@AllArgsConstructor
public class ReservationController extends BaseRestController {

    private final ReservationService reservationService;

    public ResponseEntity<Void> bookReservation(CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    public ResponseEntity<ReservationDTO> findReservation(Long reservationId)  throws Throwable{
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    public ResponseEntity<ReservationDTO> cancelReservation(Long reservationId) throws Throwable{
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    public ResponseEntity<ReservationDTO> rescheduleReservation(Long reservationId, Long scheduleId) throws Throwable{
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }
}
