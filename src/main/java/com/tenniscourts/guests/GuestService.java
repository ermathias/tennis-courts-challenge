package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
@AllArgsConstructor
public class GuestService {

    public final GuestRepository guestRepository;

    public final GuestMapper guestMapper;

    public GuestDTO findById(Long id) {
        return guestMapper.map(guestRepository.getOne(id));
    }

    public List<GuestDTO> findGuestByName(String name) {
        return guestMapper.map(guestRepository.findByNameIgnoreCaseContaining(name));
    }

    public GuestDTO addGuest(GuestDTO guestDTO) {
        return guestMapper.map(guestRepository.save(guestMapper.map(guestDTO)));
    }

    public List<GuestDTO> getAll() {
        return guestMapper.map(guestRepository.findAll());
    }

    public GuestDTO updateGuest(GuestDTO guestDTO) {
        guestRepository.findById(guestDTO.getId()).map(guestMapper::map).orElseThrow(() -> {
            throw new EntityNotFoundException(String.format("Guest with id: %s not found.", guestDTO.getId()));
        });

        return guestMapper.map(guestRepository.save(guestMapper.map(guestDTO)));
    }

    public void deleteGuest(Long id) {
        guestRepository.findById(id).ifPresentOrElse(entity -> guestRepository.delete(entity), () -> {
            throw new EntityNotFoundException(String.format("Guest with id: %s not found.", id));
        }
        );
    }

}
