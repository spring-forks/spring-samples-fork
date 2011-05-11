package org.springsource.examples.sawt.web.flex;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
public class FlexConfig {

	@Bean
	public InternalResourceViewResolver resolver() {
		InternalResourceViewResolver i = new InternalResourceViewResolver();
		i.setViewClass(JstlView.class);
		i.setPrefix("/WEB-INF/jsp/");
		i.setSuffix(".jsp");
		return i;
	}
}
