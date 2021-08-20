package com.tenniscourts.guests;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GuestService {

    public final GuestRepository guestRepository;

    public final GuestMapper guestMapper;

    public GuestDTO findById(Long id) {
        return guestMapper.map(guestRepository.getOne(id));
    }

}
