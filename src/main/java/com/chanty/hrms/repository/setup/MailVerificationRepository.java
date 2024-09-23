package com.chanty.hrms.repository.setup;

import com.chanty.hrms.model.setup.MailVerify;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailVerifyRepository extends JpaRepository<MailVerify, Long> {
    MailVerify findByEmail(String email);
    MailVerify findByToken(String token);
}
