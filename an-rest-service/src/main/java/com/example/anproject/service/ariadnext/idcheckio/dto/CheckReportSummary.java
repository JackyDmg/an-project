package com.example.anproject.service.ariadnext.idcheckio.dto;

import java.util.List;

import lombok.Data;

/**
 * The check report summary.
 */
@Data
public class CheckReportSummary {

	/** The check. */
	private List<SingleVerificationDto> check;

}
