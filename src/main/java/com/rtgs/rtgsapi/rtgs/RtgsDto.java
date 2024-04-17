package com.rtgs.rtgsapi.rtgs;

import com.rtgs.rtgsapi.dtos.branch.Branch;
import com.rtgs.rtgsapi.otherBranch.OtherBank;
import com.rtgs.rtgsapi.reasons.Reasons;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RtgsDto implements Serializable {
    private long id;
    private Long amount;
    private String otherBranch;
    private OtherBank otherBank;
    private Reasons reasons;
    private Branch branch;
    private Status status;
}
