package com.rtgs.rtgsapi.otherBranch;

import com.rtgs.rtgsapi.dtos.Branch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OtherBranchDto implements Serializable {
    private long id;
    private String name;
    private String description;
    private Branch branch;
}
