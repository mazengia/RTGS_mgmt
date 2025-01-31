package com.rtgs.rtgsapi.rtgs;

import com.rtgs.rtgsapi.dtos.Branch;
import com.rtgs.rtgsapi.otherBranch.OtherBank;
import com.rtgs.rtgsapi.reasons.Reasons;
import com.rtgs.rtgsapi.utils.Auditable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RtgsDto  extends Auditable implements Serializable  {
    private long id;
    private Long amount;
    private String otherBranch;
    private OtherBank otherBank;
    private Reasons reasons;
    private Branch branch;
    private String description;
    private Status status;
    private Status ho;
    private String remark;
    private Feedbacks feedbacks;
}
