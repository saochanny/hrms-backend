package com.chanty.hrms.model.setup;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Table(name = "bank_accounts")
@Entity
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String branch;
    @Column(unique = true, nullable = false)
    private String bank;
    @Column(unique = true)
    private String accountNo;
    private String accountName;
    private String referenceName;
}
