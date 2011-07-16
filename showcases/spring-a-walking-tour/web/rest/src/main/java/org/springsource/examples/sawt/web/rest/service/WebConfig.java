package org.springsource.examples.sawt.web.rest.service;

 import org.springframework.context.annotation.Configuration;
 import org.springframework.context.annotation.Import;
 import org.springsource.examples.sawt.services.jdbc.Config;

@Configuration
@Import(Config.class)
public class WebConfig {
}
