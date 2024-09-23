package com.chanty.hrms.model.setup;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Table(name = "mail_verification")
@Entity
public class MailVerification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "verified_at")
    private LocalDateTime verifiedAt;
    @Column(name = "email")
    private String email;
    @Column(name = "is_verified")
    private Boolean isVerified;
    @Column(name = "token")
    private String token;
}
