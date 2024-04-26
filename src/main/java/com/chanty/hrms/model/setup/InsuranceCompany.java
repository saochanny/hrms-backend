package com.chanty.hrms.model.setup;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
@Table(name = "insurance_companies")
@Data
@Entity
public class InsuranceCompany {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String code;
    @Column(unique = true )
    private String location;
    @Column(unique = true )
    private String branch;
    private String remark;
    private String companyName;
    private Boolean isActive;
    private LocalDateTime createdDate;
    private String createdBy;
}
