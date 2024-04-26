package com.chanty.hrms.model.setup;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@Table(name = "certification_types")
@Entity
public class CertificationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Column(nullable = false, unique = true)
    private String code;
    private String description;
    private String secDescription;
    private String remark;
    private Integer lck;
    private String templatePath;
    private String templatePathKh;
}
