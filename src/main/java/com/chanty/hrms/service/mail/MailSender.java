package com.chanty.hrms.service.mail;

import jakarta.mail.MessagingException;

public interface MailSender <T> {
    void send(T t) throws MessagingException;
}
