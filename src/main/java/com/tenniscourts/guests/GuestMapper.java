package com.tenniscourts.guests;

import com.tenniscourts.tenniscourts.TennisCourt;
import com.tenniscourts.tenniscourts.TennisCourtDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GuestMapper {

    GuestDTO map(Guest source);

    Guest map(GuestDTO source);

    List<GuestDTO> map(List<Guest> source);
}
