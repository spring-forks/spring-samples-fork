package org.springframework.samples;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
public class CustomerConfiguration {

  @Autowired
  private DataSource dataSource;

  @Bean
  public JdbcTemplate simpleJdbcTemplate() {
    return new JdbcTemplate(this.dataSource);
  }
}
