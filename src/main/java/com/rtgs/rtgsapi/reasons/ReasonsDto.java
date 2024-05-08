package com.rtgs.rtgsapi.reasons;

import com.rtgs.rtgsapi.dtos.Branch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReasonsDto implements Serializable {
    private long id;
    private String name;
    private Branch branch;
}
