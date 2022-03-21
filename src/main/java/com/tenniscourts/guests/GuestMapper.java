package com.tenniscourts.guests;

import java.util.List;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GuestMapper {
    
    GuestDto map(Guest source);

    @InheritInverseConfiguration
    Guest map(GuestDto source);

    List<GuestDto> map (List<Guest> source);

}
