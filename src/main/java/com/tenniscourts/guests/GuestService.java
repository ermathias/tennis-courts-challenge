package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GuestService {

    private final GuestMapper guestMapper;

    private final GuestRepository guestRepository;

    public GuestDTO findGuest(Long guestId) throws Throwable {
        return guestRepository.findById(guestId).map(guestMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException("Guest Not Found!");
        });
    }

    public List<GuestDTO> findGuestByName(String name) {
        return guestMapper.map(guestRepository.findByNameContains(name));
    }

    public List<GuestDTO> findAllGuests() {
        return guestMapper.map((guestRepository.findAll()));
    }
    public GuestDTO createGuest(GuestDTO guestDTO) {
        return guestMapper.map(guestRepository.save(guestMapper.map(guestDTO)));
    }

    public void deleteGuest(Long guestId) throws Throwable {
        Guest guest = guestRepository.findById(guestId).orElseThrow(() -> {
            throw new EntityNotFoundException("Guest Not Found!");
        });
        guestRepository.deleteById(guest.getId());
    }

    public GuestDTO updateGuest(GuestDTO guestDTO) throws Throwable {
        Guest guest = guestRepository.findById(guestDTO.getId()).orElseThrow(() -> {
            throw new EntityNotFoundException("Guest Not Found!");
        });

        return guestMapper.map(guestRepository.save(guestMapper.map(guestDTO)));
    }


}
