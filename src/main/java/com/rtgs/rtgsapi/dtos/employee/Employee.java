package com.rtgs.rtgsapi.dtos.employee;

import com.rtgs.rtgsapi.dtos.Branch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private String employeeId;
    String firstName;
    String middleName;
    private Branch branch;

}