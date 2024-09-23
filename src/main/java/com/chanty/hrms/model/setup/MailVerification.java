package com.chanty.hrms.model.setup;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "mail_verifies")
@Entity
public class MailVerify {
    @Id
    private Long id;
    private LocalDateTime verifyAt;
    private String email;
    private Boolean isVerified;
    private String token;
}
