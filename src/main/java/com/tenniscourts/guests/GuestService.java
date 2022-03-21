package com.tenniscourts.guests;

import java.util.List;

import com.tenniscourts.exceptions.EntityNotFoundException;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class GuestService {
    private final GuestRepository guestRepository;

    private final GuestMapper guestMapper;

    public GuestDto addGuest(GuestDto guest){
        return guestMapper.map(guestRepository.saveAndFlush(guestMapper.map(guest)));
    }

    public GuestDto findGuestById(Long id) {
        return guestRepository.findById(id).map(guestMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found.");
        });
    }

    public List<GuestDto> findGuestByName(String name) {
        return guestMapper.map(guestRepository.findByNameIgnoreCaseContaining(name));
    }

    public List<GuestDto> findAll(){
        return guestMapper.map(guestRepository.findAll());
    }

    public GuestDto updateGuest(GuestDto guest){
        guestRepository.findById(guest.getId()).map(guestMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Guest not found");
        });

        return guestMapper.map(guestRepository.save(guestMapper.map(guest)));
    }

    public void removeGuest(Long id){
        guestRepository.findById(id).ifPresentOrElse(entity -> guestRepository.delete(entity), () -> {
            throw new EntityNotFoundException("Guest not found");
        }
        );
    }

}
