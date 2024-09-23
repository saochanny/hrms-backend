package com.chanty.hrms.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "spring.engine.template")
@Component
@Getter
@Setter
public class TemplateEngineProperties {
    private String prefix;
    private String suffix;
}

