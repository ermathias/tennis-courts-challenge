package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/guest")
public class GuestController extends BaseRestController {
	
	@Autowired
	private GuestService guestService;
	
	@PostMapping("/add")
	public ResponseEntity<GuestDTO> addGuest(@RequestBody GuestDTO guestDto){
		GuestDTO guest=new GuestDTO();
		try {
			guest=guestService.save(guestDto);
		      return new ResponseEntity<GuestDTO>(guest, HttpStatus.CREATED);
		}catch (Exception e) {
		      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
		
	}
	@GetMapping("/all")
	public ResponseEntity<List<GuestDTO>> findAllguests(){
		List<GuestDTO> guestList=guestService.finalAllGuests();
			return new ResponseEntity<List<GuestDTO>>(guestList, HttpStatus.CREATED);
		}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@PathVariable("id") Long guestId){
		GuestDTO guest= new GuestDTO();
		try {
			 guest=guestService.findGuestById(guestId);
			 System.out.println("Guest---------" +guest);
			if(null!=guest)
				return ResponseEntity.status(HttpStatus.OK).body(guest);
			else
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Guest is not Found");

		}catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@GetMapping("/name/{guestName}")
	public ResponseEntity<?> findByName(@PathVariable("guestName") String guestName){
		List<GuestDTO> guest=new ArrayList<GuestDTO>();
		try {
			guest=guestService.findGuestsByName(guestName);
			if(null!=guest)
				return ResponseEntity.status(HttpStatus.OK).body(guest);
			else
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Guest is not Found");

		}catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteGuest(@PathVariable("id") Long id){
		try{
			guestService.deleteGuest(id);
			return ResponseEntity.status(HttpStatus.OK).body("Guest deleted Successfully");
		}catch (Exception e) {
		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	}

	@DeleteMapping("/name")
	public ResponseEntity<String> deleteGuest(@RequestBody GuestDTO guestDto){
		try{
			guestService.deleteGuestByName(guestDto);
			return ResponseEntity.status(HttpStatus.OK).body("Guest deleted Successfully");
		}catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PutMapping("/update")
	public ResponseEntity<String> updateGuest(@RequestBody GuestDTO guestDto){
		try{
			GuestDTO guestdto=guestService.updateGuest(guestDto);
			if(null!=guestdto)
			return ResponseEntity.status(HttpStatus.OK).body("Guest Updated Successfully");
			else
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Guest is not Found");
		}catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
