package com.example.anproject.service.thirdparty.ariadnext.idcheckio.client;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

/**
 * The IdCheckIo Properties.
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ariadnext.idcheckio.client")
public class IdCheckIoProperties {

	/** The username. */
	private String username;

	/** The password. */
	private String password;

	/** The plateform. */
	private String plateform;
}