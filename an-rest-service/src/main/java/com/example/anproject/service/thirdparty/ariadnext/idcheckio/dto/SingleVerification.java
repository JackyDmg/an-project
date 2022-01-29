package com.example.anproject.service.thirdparty.ariadnext.idcheckio.dto;

import lombok.Data;

/**
 * The single verification.
 */
@Data
public class SingleVerification {

	/** The identifier. */
	// TODO use Enum
	private String identifier;

	/** The title msg. */
	private String titleMsg;

	/** The result msg. */
	private String resultMsg;

	/** The result. */
	// TODO use Enum
	private String result;

}
