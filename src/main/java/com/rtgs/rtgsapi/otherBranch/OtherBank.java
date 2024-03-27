package com.rtgs.rtgsapi.otherBranch;

import com.rtgs.rtgsapi.dtos.branch.Branch;
import com.rtgs.rtgsapi.utils.Auditable;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;

@Data
@Entity(name = "other_bank")
@Table(name = "other_bank")
@SQLDelete(sql = "UPDATE other_bank SET deleted = 'true'  WHERE id=? and version=?")
@Where(clause = "deleted=false")

public class OtherBank extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String description;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "branch_name")),
            @AttributeOverride(name = "code", column = @Column(name = "branch_code"))
    })
    private Branch branch;
}
