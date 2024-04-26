package com.chanty.hrms.model.setup;

import jakarta.persistence.*;
import lombok.Data;

@Table
@Entity
@Data
public class ProbationType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String code;
    private String description;
    private Integer inMonth;
    private Integer day;

}
