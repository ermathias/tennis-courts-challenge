package com.tenniscourts.guests;

import java.util.List;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/guest")
public class GuestController extends BaseRestController {

    private final GuestService guestService;
    
    @ApiOperation("Add Guest")
    @PostMapping(value="/addGuest", consumes="application/json")
    public @ResponseBody ResponseEntity<Void> addGuest(@RequestBody GuestDto guestDto) {
        return ResponseEntity.created(locationByEntity(guestService.addGuest(guestDto).getId())).build();
    }

    @ApiOperation("Find All Guests")
    @GetMapping(value="/findGuests/")
    public @ResponseBody ResponseEntity<List<GuestDto>> findAll() {
        return ResponseEntity.ok(guestService.findAll());
    }

    @ApiOperation("Find Guest by Id")
    @GetMapping(value="/findGuest/{id}")
    public @ResponseBody ResponseEntity<GuestDto> findGuestById(@PathVariable Long id) {
        return ResponseEntity.ok(guestService.findGuestById(id));
    }

    @ApiOperation("Find Guest by Name")
    @GetMapping(value="/findGuest/{name}")
    public @ResponseBody ResponseEntity<List<GuestDto>> findGuestByName(@PathVariable String name) {
        return ResponseEntity.ok(guestService.findGuestByName(name));
    }


    @ApiOperation("Update Guest")
    @PostMapping(value="/updateGuest", consumes="application/json")
    public @ResponseBody ResponseEntity<GuestDto> updateGuest(@RequestBody GuestDto guestDto) {
        return ResponseEntity.ok(guestService.updateGuest(guestDto));
    }


    @ApiOperation("Remove Guest by Id")
    @GetMapping(value="/removeGuest/{id}")
    public @ResponseBody void removeGuest(@PathVariable Long id) {
        guestService.removeGuest(id);
    }

    
}
