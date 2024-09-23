package com.chanty.hrms.repository.setup;

import com.chanty.hrms.model.setup.MailVerification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailVerificationRepository extends JpaRepository<MailVerification, Long> {
    MailVerification findByEmail(String email);
    MailVerification findByToken(String token);
}
