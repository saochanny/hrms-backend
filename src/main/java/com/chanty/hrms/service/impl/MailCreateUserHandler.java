package com.chanty.hrms.service.impl;

import com.chanty.hrms.common.constant.SubjectMailConstant;
import com.chanty.hrms.dto.user.CreateUserMailRequest;
import com.chanty.hrms.exception.MailException;
import com.chanty.hrms.model.setup.MailVerification;
import com.chanty.hrms.properties.MailTemplateProperties;
import com.chanty.hrms.properties.MailVariable;
import com.chanty.hrms.service.mail.MailSender;
import com.chanty.hrms.service.token.TokenService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailCreateUserHandler implements MailSender<CreateUserMailRequest> {
  private final JavaMailSender javaMailSender;
  private final TemplateEngine templateEngine;
  private final MailTemplateProperties mailTemplate;
  private final MailVerificationService mailVerificationService;
  private final TokenService tokenService;

  @Override
  public void send(CreateUserMailRequest mailRequest) {
    try {
      javaMailSender.send(this.getMessage(mailRequest, mailTemplate.getMailCreateUser()));
    } catch (MessagingException e) {
      log.error("error occur sending mail : {}", e.getMessage());
      throw new MailException(e);
    }
  }

  private MimeMessage getMessage(CreateUserMailRequest user, String htmlPath)
      throws MessagingException {
    var message = javaMailSender.createMimeMessage();
    MimeMessageHelper messageHelper =
        new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
    this.prepareMailHelper(user, messageHelper, htmlPath);
    return message;
  }

  private void prepareMailHelper(
      CreateUserMailRequest user, MimeMessageHelper mimeMessageHelper, String htmlPath)
      throws MessagingException {
    String subject = SubjectMailConstant.CREATE_USER;
    Context context = this.getContext(user, subject);
    String html = templateEngine.process(htmlPath, context);
    mimeMessageHelper.setSubject(subject);
    mimeMessageHelper.setText(html, true);
    mimeMessageHelper.setTo(user.getEmail());
  }

  private Context getContext(CreateUserMailRequest user, String subject) {
    String token = tokenService.generateToken(user.getEmail());
    MailVerification mailVerification = new MailVerification();
    mailVerification.setEmail(user.getEmail());
    mailVerification.setIsVerified(false);
    mailVerification.setToken(token);
    mailVerificationService.save(mailVerification);
    Context context = new Context();
    context.setVariable(MailVariable.SUBJECT, subject);
    context.setVariable(MailVariable.EMAIL, user.getEmail());
    context.setVariable(MailVariable.PASSWORD, user.getPassword());
    context.setVariable(
        MailVariable.FULL_NAME, String.format("%s %s", user.getFirstName(), user.getLastName()));
    context.setVariable(MailVariable.VERIFICATION_LINK, tokenService.buildLink(token));
    return context;
  }
}
