package com.rtgs.rtgsapi.reasons;

import com.rtgs.rtgsapi.dtos.branch.Branch;
import com.rtgs.rtgsapi.utils.Auditable;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;

@Data
@Entity(name = "reasons")
@Table(name = "reasons")
@SQLDelete(sql = "UPDATE reasons SET deleted = 'true'  WHERE id=? and version=?")
@Where(clause = "deleted=false")

public class Reasons extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "branch_name")),
            @AttributeOverride(name = "code", column = @Column(name = "branch_code"))
    })
    private Branch branch;
}
