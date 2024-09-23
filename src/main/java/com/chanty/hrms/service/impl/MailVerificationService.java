package com.chanty.hrms.service.impl;

import com.chanty.hrms.exception.MailVerificationException;
import com.chanty.hrms.exception.ResourceNotFoundException;
import com.chanty.hrms.model.setup.MailVerification;
import com.chanty.hrms.model.setup.User;
import com.chanty.hrms.repository.setup.MailVerificationRepository;
import com.chanty.hrms.repository.setup.UserRepository;
import com.chanty.hrms.service.token.TokenService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/** Service implementation for handling mail verification related operations. */
@Service
@RequiredArgsConstructor
@Slf4j
public class MailVerificationService {
  private final MailVerificationRepository mailVerificationRepository;
  private final TokenService tokenService;
  private final PasswordEncoder passwordEncoder;
  private final UserRepository userRepository;
  private final WebSocketServletAutoConfiguration webSocketServletAutoConfiguration;

  /**
   * Saves a MailVerification object to the repository.
   *
   * @param mailVerification the MailVerification object to save
   */
  public void save(MailVerification mailVerification) {
    log.info("==== save mailVerify :{} ", mailVerification);
    mailVerificationRepository.save(mailVerification);
  }

  /**
   * Finds a MailVerification by its ID.
   *
   * @param id the ID of the MailVerification to find
   * @return the MailVerification object
   * @throws MailVerificationException if the MailVerification is not found
   */
  public MailVerification findById(Long id) {
    log.info("==== find mailVerify by id ===");
    return mailVerificationRepository
        .findById(id)
        .orElseThrow(() -> new MailVerificationException("Mail verification not found!"));
  }

  /**
   * Finds a MailVerification by email.
   *
   * @param email the email associated with the MailVerification
   * @return the MailVerification object
   */
  public MailVerification findByEmail(String email) {
    log.info("==== find mailVerify by email ===");
    return mailVerificationRepository.findByEmail(email);
  }

  /**
   * Creates a password for the user associated with the given token.
   *
   * @param token the token to verify
   * @param password the new password to set
   * @throws MailVerificationException if the token is invalid or expired, or if the
   *     MailVerification is already verified
   * @throws ResourceNotFoundException if the user is not found
   */
  public void createPassword(String token, String password) {
    checkValidJwt(token);
    log.info("==== create password ===");
    log.info("====>>>find mail verification by token:{}", token);
    MailVerification mailVerification = mailVerificationRepository.findByToken(token);
    checkVerified(mailVerification);
    mailVerification.setVerifiedAt(LocalDateTime.now());
    mailVerification.setIsVerified(true);
    mailVerificationRepository.save(mailVerification);

    //update user create password to user

    log.info("===== find user by email : {} ", mailVerification.getEmail());
    User user =
        userRepository
            .findByEmail(mailVerification.getEmail())
            .orElseThrow(() -> new ResourceNotFoundException("user", mailVerification.getEmail()));
    user.setPassword(passwordEncoder.encode(password));
    userRepository.save(user);
    log.info("====== Password have changed!!!");

    log.info("==== Verified mail successfully!!!");
  }

  /**
   * Verifies the given token.
   *
   * @param token the token to verify
   * @throws MailVerificationException if the token is invalid or expired, or if the
   *     MailVerification is already verified
   */
  public void verifyLink(String token) {
    checkValidJwt(token);
    MailVerification mailVerification = mailVerificationRepository.findByToken(token);
    checkVerified(mailVerification);
  }

  /**
   * Checks if the given JWT token is valid.
   *
   * @param jwt the JWT token to check
   * @throws MailVerificationException if the token is invalid or expired
   */
  private void checkValidJwt(String jwt) {
    if (!tokenService.validateJwtToken(jwt)) {
      log.warn("====== Mail verification link is invalid expired!");
      throw new MailVerificationException("Mail verification link is invalid or expired!");
    }
  }

  /**
   * Checks if the given MailVerification object is valid and not already verified.
   *
   * @param mailVerification the MailVerification object to check
   * @throws MailVerificationException if the MailVerification is null or already verified
   */
  private void checkVerified(MailVerification mailVerification) {
    if (mailVerification == null) {
      log.warn("====== Mail verification link is not found!");
      throw new MailVerificationException("Mail verification link is not found!");
    }
    if (Boolean.TRUE.equals(mailVerification.getIsVerified())) {
      log.warn("====== Mail verification link is verified!");
      throw new MailVerificationException("Mail verification link is verified!");
    }
  }
}
