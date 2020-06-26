package com.kakaopay.wallet.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.view.BeanNameViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.fasterxml.jackson.databind.SerializationFeature;

@EnableWebMvc
@Configuration
@ComponentScan
@ConditionalOnWebApplication
public class WebConfig extends WebMvcConfigurationSupport {

	private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);

	@Bean
	public ViewResolver viewResolver() {
		return new BeanNameViewResolver();
	}

	@Bean(name="jsonView")
	public View jsonView() {
		MappingJackson2JsonView view = new MappingJackson2JsonView();
		view.setPrettyPrint(false);

		CustomObjectMapper mapper = new CustomObjectMapper();

	    mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	    mapper.disable(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS);

		view.setObjectMapper(mapper);
		return view;
	}
}
