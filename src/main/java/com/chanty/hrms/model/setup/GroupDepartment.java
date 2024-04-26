package com.chanty.hrms.model.setup;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Entity
@Table(name = "group_departments")
public class GroupDepartment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Column(unique = true, nullable = false)
    private String code;
    private String description;
    private String secDescription;
    private String remark;
    private Integer lck;
    private String sortKey;
}
