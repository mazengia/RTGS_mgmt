package com.rtgs.rtgsapi.reasons;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReasonsMapper {
    Reasons toReasons(ReasonsDto reasonsDto);
    ReasonsDto toReasonsDto(Reasons reasons);
}
