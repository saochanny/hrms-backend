package com.chanty.hrms.controller;

import com.chanty.hrms.dto.mail_verify.PasswordVerify;
import com.chanty.hrms.service.impl.MailVerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/mail-verifies")
@RequiredArgsConstructor
public class MailVerifiedController {
  private final MailVerificationService mailVerificationService;

  @PostMapping("/create-password")
  public void createPassword(@RequestBody PasswordVerify passwordVerify) {
    mailVerificationService.createPassword(passwordVerify.getToken(), passwordVerify.getPassword());
  }

  @GetMapping
  public void verifyEmail(@RequestParam(name = "token") String token) {
    mailVerificationService.verifyLink(token);
  }
}
