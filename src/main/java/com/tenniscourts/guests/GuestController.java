package com.tenniscourts.guests;


import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(value="/guests")
public class GuestController extends BaseRestController {

    private final GuestService guestService;

    @GetMapping(value="/{id}")
    public ResponseEntity<GuestDTO> findGuest(@PathVariable("id") Long guestId) throws Throwable{
        return ResponseEntity.ok(guestService.findGuest(guestId));
    }

    @GetMapping(value="/names/{name}")
    public ResponseEntity<List<GuestDTO>> findGuestsByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(guestService.findGuestByName(name));
    }

    @GetMapping(value = "/all_guests")
    public ResponseEntity<List<GuestDTO>> findAllGuests() {
        return  ResponseEntity.ok(guestService.findAllGuests());
    }

    @PostMapping(value="/create_guest")
    public ResponseEntity<Void> createGuest(@RequestBody GuestDTO guestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.createGuest(guestDTO).getId())).build();
    }

    @DeleteMapping(value="/{id}")
    public ResponseEntity<Void> deleteGuest(@PathVariable("id") Long guestId) throws Throwable{
        guestService.deleteGuest(guestId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value="/update_guest")
    public ResponseEntity<GuestDTO> updateGuest(@RequestBody GuestDTO guestDTO) throws Throwable {
        return ResponseEntity.ok(guestService.updateGuest(guestDTO));
    }

}
