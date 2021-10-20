package com.tenniscourts.guests;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GuestService {

    private final GuestRepository guestRepository;
    private final GuestMapper guestMapper;
	public GuestDTO save(GuestDTO guestDto) {
		return guestMapper.map(guestRepository.save(guestMapper.map(guestDto)));
	}
	public GuestDTO findGuestById(Long id){
		return guestMapper.map(guestRepository.findById(id).get());
	}
	public List<GuestDTO> finalAllGuests(){
		return guestMapper.map(guestRepository.findAll());
	}
	public List<GuestDTO> findGuestsByName(String guestName){
		return guestMapper.map(guestRepository.findByName(guestName));
	}
	public void deleteGuest(Long id){
		 guestRepository.deleteById(id);
	}
	public void deleteGuestByName(GuestDTO guestDto){
		guestRepository.delete(guestMapper.map(guestDto));
	}
	public GuestDTO updateGuest(GuestDTO guestDto){
		GuestDTO guestDTO=null;
		Guest guest=guestRepository.findById(guestDto.getId()).get();
		if(null!=guest.getId() && null!=guest.getName())
			guestDTO=guestMapper.map(guestRepository.save(guestMapper.map(guestDto)));
		return guestDTO;
	}

}
