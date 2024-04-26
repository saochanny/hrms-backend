package com.chanty.hrms.model.setup;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Table(name = "notifications")
@Entity
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String alertType;
    @Column(unique = true, nullable = false)
    private String empCode;
    private String empName;
    private String gender;

    @Column(unique = true)
    private String department;
    @Column(unique = true)
    private String position;
    private LocalDate startDate;
    private LocalDate endDate;

}
