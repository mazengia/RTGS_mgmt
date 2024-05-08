package com.rtgs.rtgsapi.rtgs;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RtgsMapper {
    Rtgs toRtgs(RtgsDto rtgsDto);

    RtgsDto toRtgsDto(Rtgs rtgs);
}
