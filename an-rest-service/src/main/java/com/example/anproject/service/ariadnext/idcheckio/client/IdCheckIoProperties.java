package com.example.anproject.service.ariadnext.idcheckio.client;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * The Class IdCheckIoProperties.
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ariadnext.idcheckio.client")
public class IdCheckIoProperties {

	private String username;
	private String password;
}