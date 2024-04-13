package com.rtgs.rtgsapi.rtgs;

import com.rtgs.rtgsapi.dtos.branch.Branch;
import com.rtgs.rtgsapi.otherBranch.OtherBank;
import com.rtgs.rtgsapi.utils.Auditable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Data
@Entity
@Table
//@SQLDelete(sql = "UPDATE rtgs SET deleted = 'true'  WHERE id=? and version=?")
//@Where(clause = "deleted=false")

public class Rtgs extends Auditable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private OtherBank otherBank;
    private Long amount;
    private String otherBranch;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "branch_name")),
            @AttributeOverride(name = "code", column = @Column(name = "branch_code"))
    })
    private Branch branch;

}
