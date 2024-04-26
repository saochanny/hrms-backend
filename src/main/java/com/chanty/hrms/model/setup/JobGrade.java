package com.chanty.hrms.model.setup;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Table(name = "job_grades")
@Entity
@Data
public class JobGrade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String levelCode;
    @Column(unique = true, nullable = false)
    @NotEmpty
    private String code;
    private String description;
    private String secDescription;
    private String remark;
    private Integer lck;

}
