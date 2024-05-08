package com.rtgs.rtgsapi.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Branch {
    private String name;
    private String code;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "code", column = @Column(name = "district_code")),
            @AttributeOverride(name = "name", column = @Column(name = "district_name"))
    })
    private District district;
}