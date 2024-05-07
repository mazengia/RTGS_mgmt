package com.rtgs.rtgsapi.rtgs;

import com.rtgs.rtgsapi.converters.FeedbacksConverter;
import com.rtgs.rtgsapi.dtos.Branch;
import com.rtgs.rtgsapi.otherBranch.OtherBank;
import com.rtgs.rtgsapi.reasons.Reasons;
import com.rtgs.rtgsapi.utils.Auditable;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.io.Serializable;

@Data
@Entity
@Table
@SQLDelete(sql = "UPDATE rtgs SET deleted = 'true'  WHERE id=? and version=?")
@Where(clause = "deleted=false")

public class Rtgs  extends Auditable implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private OtherBank otherBank;
    @ManyToOne
    private Reasons reasons;

    private Long amount;
    private String otherBranch;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Status ho;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "branch_name")),
            @AttributeOverride(name = "code", column = @Column(name = "branch_code"))
    })
    private Branch branch;
    private String description;
    private String remark;
    @Convert(converter = FeedbacksConverter.class)
    @Lob
    private Feedbacks feedbacks;

}
