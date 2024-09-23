package com.chanty.hrms.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "spring.mail")
@Component
public class JavaMailProperties {
  private String host;
  private String port;
  private String username;
  private String password;
}
