package com.chanty.hrms.model.setup;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "medical_types")
@Entity
@Data
public class MedicalType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String code;
    private String description;
    private String secDescription;
    private String remark;
}
