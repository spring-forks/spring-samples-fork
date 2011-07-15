package org.springsource.examples.sawt.web.mvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

@Configuration
@Import( org.springsource.examples.sawt.web.mvc.WebConfig.class)
public class WebConfig {

	@Bean
	public UrlBasedViewResolver resolver() {
		UrlBasedViewResolver url = new UrlBasedViewResolver();
		url.setPrefix("views/");
		url.setViewClass(JstlView.class);
		url.setSuffix(".jsp");
		return url;
	}
}
