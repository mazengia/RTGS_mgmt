package com.rtgs.rtgsapi.dtos.employee;

import com.rtgs.rtgsapi.dtos.branch.Branch;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    private long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String employeeId;
    private Branch branch;
}