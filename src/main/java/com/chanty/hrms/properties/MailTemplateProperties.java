package com.chanty.hrms.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "app.mail-template")
@Component
@Setter
@Getter
public class MailTemplateProperties {
  private String mailCreateUser;
}
