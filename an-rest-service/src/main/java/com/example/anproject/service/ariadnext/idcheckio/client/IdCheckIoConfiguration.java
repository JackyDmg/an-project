package com.example.anproject.service.ariadnext.idcheckio.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import feign.Logger;
import feign.auth.BasicAuthRequestInterceptor;
import feign.slf4j.Slf4jLogger;

/**
 * The Class IdCheckIoConfiguration.
 */
public class IdCheckIoConfiguration {

	/** The id check io properties. */
	@Autowired
	private IdCheckIoProperties idCheckIoProperties;

	/**
	 * Feign logger level.
	 *
	 * @return the logger. level
	 */
	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}

	/**
	 * Feign logger.
	 *
	 * @return the slf 4 j logger
	 */
	@Bean
	Slf4jLogger feignLogger() {
		return new Slf4jLogger();
	}

	/**
	 * Basic auth request interceptor.
	 *
	 * @return the basic auth request interceptor
	 */
	@Bean
	public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
		return new BasicAuthRequestInterceptor(idCheckIoProperties.getUsername(), idCheckIoProperties.getPassword());
	}

}
