package com.chanty.hrms.service.impl;

import com.chanty.hrms.exception.MailVerificationException;
import com.chanty.hrms.model.setup.MailVerify;
import com.chanty.hrms.repository.setup.MailVerifyRepository;
import com.chanty.hrms.service.token.TokenService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j

public class MailVerifyService {
  private final MailVerifyRepository mailVerifyRepository;
  private final TokenService tokenService;
  public void save(MailVerify mailVerify) {
    log.info("==== save mailVerify ===");
    mailVerifyRepository.save(mailVerify);
  }

  public MailVerify findById(Long id) {
    log.info("==== find mailVerify by id ===");
    return mailVerifyRepository
        .findById(id)
        .orElseThrow(() -> new MailVerificationException("Mail verification not found!"));
  }
  public MailVerify findByEmail(String email) {
    log.info("==== find mailVerify by email ===");
    return mailVerifyRepository.findByEmail(email);
  }
  public void verified(String token) {
    if(!tokenService.validateJwtToken(token)){
      log.warn("====== Mail verification link is expired!");
      throw new MailVerificationException("Mail verification link is expired!");
    }
    MailVerify mailVerify = mailVerifyRepository.findByToken(token);
    if (mailVerify == null) {
      log.error("==== MailVerify not found ===");
      throw new MailVerificationException("Mail verification link is not valid!");
    }
    mailVerify.setVerifyAt(LocalDateTime.now());
    mailVerify.setIsVerified(true);
    mailVerifyRepository.save(mailVerify);
    log.info("==== Verified mail ===");
  }
}
