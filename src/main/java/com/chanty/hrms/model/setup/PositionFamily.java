package com.chanty.hrms.model.setup;

import jakarta.persistence.*;
import lombok.Data;

@Table(name = "position_families")
@Entity
@Data
public class PositionFamily {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String code;
    private String description;
    private String secDescription;
    private String remark;
    private Integer lck;
    private Boolean isActive;

}
