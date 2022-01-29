package com.example.anproject.service.thirdparty.ariadnext.idcheckio.dto;

import lombok.Data;

/**
 * The analysis result.
 */
@Data
public class AnalysisResult {

	/** The uid. */
	private String uid;

	/** The analysis ref uid. */
	private String analysisRefUid;

	/** The check report summary. */
	private CheckReportSummary checkReportSummary;

	/** The document classification. */
	private DocumentClassification documentClassification;

	/** The document detail. */
	private DocumentDetail documentDetail;

	/** The holder detail. */
	private HolderDetail holderDetail;

}
