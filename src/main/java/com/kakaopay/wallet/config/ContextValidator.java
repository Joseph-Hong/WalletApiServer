package com.kakaopay.wallet.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springmodules.validation.commons.DefaultBeanValidator;
import org.springmodules.validation.commons.DefaultValidatorFactory;
import org.springmodules.validation.commons.ValidatorFactory;

@Configuration
public class ContextValidator {
	private static final Logger logger = LoggerFactory.getLogger(ContextValidator.class);

	@Autowired
	ApplicationContext ac;

	@Bean
	public DefaultBeanValidator beanValidator(ValidatorFactory validatorFactory) {
		DefaultBeanValidator defaultBeanValidator = new DefaultBeanValidator();
		defaultBeanValidator.setValidatorFactory(validatorFactory);
		return defaultBeanValidator;
	}

	@Bean
	public DefaultValidatorFactory validatorFactory() {
		DefaultValidatorFactory defaultValidatorFactory = new DefaultValidatorFactory();

		defaultValidatorFactory.setValidationConfigLocations(
			new Resource[] {
				ac.getResource("classpath:validator/validator-rules.xml")
				, ac.getResource("classpath:validator/validator.xml")
			}
		);

		return defaultValidatorFactory;
	}
}
