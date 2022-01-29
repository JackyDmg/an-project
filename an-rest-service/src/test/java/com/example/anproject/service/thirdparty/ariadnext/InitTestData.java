package com.example.anproject.service.thirdparty.ariadnext;

import java.util.ArrayList;
import java.util.List;

import com.example.anproject.service.thirdparty.ariadnext.idcheckio.dto.AnalysisResult;
import com.example.anproject.service.thirdparty.ariadnext.idcheckio.dto.Date;
import com.example.anproject.service.thirdparty.ariadnext.idcheckio.dto.DocumentClassification;
import com.example.anproject.service.thirdparty.ariadnext.idcheckio.dto.DocumentDetail;
import com.example.anproject.service.thirdparty.ariadnext.idcheckio.dto.HolderDetail;
import com.example.anproject.service.thirdparty.ariadnext.idcheckio.dto.UserResponse;

/**
 * The Class InitTestData.
 */
public class InitTestData {

	public static UserResponse buildUserResponse(int credits) {

		UserResponse userResponse = new UserResponse();
		userResponse.setRemainingCredits(credits);
		return userResponse;

	}

	public static AnalysisResult buidlAnalysisResult() {
		AnalysisResult analysis = new AnalysisResult();

		HolderDetail holderDetail = new HolderDetail();
		List<String> firstNames = new ArrayList<>();
		firstNames.add("John");
		holderDetail.setFirstName(firstNames);
		List<String> lastNames = new ArrayList<>();
		lastNames.add("Doe");
		holderDetail.setLastName(lastNames);

		holderDetail.setNationality("FR");

		Date birthDate = new Date();
		birthDate.setDay("1");
		birthDate.setMonth("1");
		birthDate.setYear("1970");
		holderDetail.setBirthDate(birthDate);

		analysis.setHolderDetail(holderDetail);

		DocumentClassification docClass = new DocumentClassification();
		docClass.setIdType("ID");
		analysis.setDocumentClassification(docClass);

		DocumentDetail docDetail = new DocumentDetail();
		docDetail.setDocumentNumber("123456789");
		analysis.setDocumentDetail(docDetail);

		return analysis;
	}
}
