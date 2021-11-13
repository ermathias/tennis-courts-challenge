package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import com.tenniscourts.guests.GuestDTO;
import com.tenniscourts.guests.GuestService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/guest")
public class GuestController extends BaseRestController {

   //5. As a Tennis Court Admin, I want to be able to Create/Update/Delete/Find by id/Find by name/List all the guests
    private final GuestService guestService;

    @PostMapping
    public ResponseEntity<Void> addGuest(@RequestBody GuestDTO guestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.addGuest(guestDTO).getId())).build();
    }

    @GetMapping("/{guestId}")
    public ResponseEntity<GuestDTO> findGuestById(@PathVariable("guestId") Long guestId) {
        return ResponseEntity.ok(guestService.findGuestById(guestId));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<GuestDTO>> findGuestByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(guestService.findGuestByName(name));
    }

    @PutMapping("/{guestId}/name/{name}")
    public ResponseEntity<GuestDTO> updateGuest(@PathVariable("guestId") Long guestId, @PathVariable("name") String name) {
        return ResponseEntity.ok(guestService.updateGuest(guestId, name));
    }

    @DeleteMapping("/{guestId}")
    public void deleteGuestById(@PathVariable("guestId") Long guestId) {
        guestService.deleteGuestById(guestId);
    }
}
