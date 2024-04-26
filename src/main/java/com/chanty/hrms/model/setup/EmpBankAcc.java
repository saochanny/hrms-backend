package com.chanty.hrms.model.setup;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.math.BigDecimal;

@Table(name = "employee_bank_accounts")
@Entity
@Data
public class EmpBankAcc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    @Column(unique = true, nullable = false)
    private String empCode;
    private Integer lineItem;
    private String bankName;
    private String bankAccount;
    private BigDecimal salary;
    private Boolean isTax;
}
