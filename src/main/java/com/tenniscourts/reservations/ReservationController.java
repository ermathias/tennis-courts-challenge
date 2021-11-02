package com.tenniscourts.reservations;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/reservation")
public class ReservationController extends BaseRestController {

    private final ReservationService reservationService;

    @PostMapping(value="/book_reservation")
    public ResponseEntity<Void> bookReservation(@RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
        return ResponseEntity.created(locationByEntity(reservationService.bookReservation(createReservationRequestDTO).getId())).build();
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<ReservationDTO> findReservation(@PathVariable("id") Long reservationId) throws Throwable {
        return ResponseEntity.ok(reservationService.findReservation(reservationId));
    }

    @DeleteMapping(value="/delete/{id}")
    public ResponseEntity<ReservationDTO> cancelReservation(@PathVariable("id") Long reservationId) throws Throwable {
        return ResponseEntity.ok(reservationService.cancelReservation(reservationId));
    }

    @PutMapping(value="/reschedule/reservationId/{rid}/scheduleId/{sid}")
    public ResponseEntity<ReservationDTO> rescheduleReservation(@PathVariable("rid")Long reservationId, @PathVariable("sid") Long scheduleId) throws Throwable {
        return ResponseEntity.ok(reservationService.rescheduleReservation(reservationId, scheduleId));
    }
}
