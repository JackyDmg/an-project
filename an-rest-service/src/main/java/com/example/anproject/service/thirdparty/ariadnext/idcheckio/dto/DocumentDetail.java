package com.example.anproject.service.thirdparty.ariadnext.idcheckio.dto;

import lombok.Data;

/**
 * The document detail.
 */
@Data
public class DocumentDetail {

	/** The emit country. */
	private String emitCountry;

	/** The emit date. */
	private Date emitDate;

	/** The expiration date. */
	private Date expirationDate;

	/** The document number. */
	private String documentNumber;

}
