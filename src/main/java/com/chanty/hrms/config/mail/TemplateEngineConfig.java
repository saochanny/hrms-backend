package com.chanty.hrms.config.mail;

import com.chanty.hrms.properties.TemplateEngineProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.spring6.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

@Configuration
public class TemplateEngineConfig {
  private final String prefix;
  private final String suffix;

  public TemplateEngineConfig(TemplateEngineProperties properties) {
    this.prefix = properties.getPrefix();
    this.suffix = properties.getSuffix();
  }

  @Bean
  public SpringTemplateEngine springTemplateEngine() {
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.setTemplateResolver(templateResolver());
    return templateEngine;
  }

  @Bean
  public SpringResourceTemplateResolver templateResolver() {
    SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
    resolver.setPrefix(prefix);
    resolver.setSuffix(suffix);
    resolver.setTemplateMode(TemplateMode.HTML);
    resolver.setCharacterEncoding("UTF-8");
    return resolver;
  }
}
