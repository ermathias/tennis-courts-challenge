package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("Guest")
@RestController
@AllArgsConstructor
@RequestMapping("/guest")
public class GuestController extends BaseRestController {

    private final GuestService guestService;

    @PostMapping
    public ResponseEntity<Void> addGuest(@RequestBody GuestDTO guestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.addGuest(guestDTO).getId())).build();
    }

    @PutMapping
    public ResponseEntity<GuestDTO> updateGuest(@RequestBody GuestDTO guestDTO) {
        return ResponseEntity.ok(guestService.updateGuest(guestDTO));
    }

    @DeleteMapping(value="{id}")
    public void deleteGuest(@PathVariable Long id) {
        guestService.deleteGuest(id);
    }

    @GetMapping(value="byname/{name}")
    public ResponseEntity<List<GuestDTO>> findGuestByName(@PathVariable String name) {
        return ResponseEntity.ok(guestService.findGuestByName(name));
    }

    @GetMapping(value="all")
    public ResponseEntity<List<GuestDTO>> getAll() {
        return ResponseEntity.ok(guestService.getAll());
    }

    @GetMapping(value="{id}")
    public ResponseEntity<GuestDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(guestService.findById(id));
    }

}
