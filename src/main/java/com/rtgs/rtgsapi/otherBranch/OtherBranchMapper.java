package com.rtgs.rtgsapi.otherBranch;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OtherBranchMapper {
    OtherBank toOtherBranch(OtherBranchDto otherBranchDto);
    OtherBranchDto toOtherBranchDto(OtherBank otherBank);
}
