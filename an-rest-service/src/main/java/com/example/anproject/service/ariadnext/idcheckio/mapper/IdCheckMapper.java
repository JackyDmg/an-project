package com.example.anproject.service.ariadnext.idcheckio.mapper;

import java.time.LocalDate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.example.anproject.service.ariadnext.idcheckio.dto.AnalysisResult;
import com.example.anproject.service.ariadnext.idcheckio.dto.Date;
import com.example.anproject.service.ariadnext.idcheckio.dto.HolderDetail;
import com.example.anproject.service.user.bo.UserId;

/**
 * The Class IdCheckMapper.
 */
@Component
public class IdCheckMapper {

	/**
	 * User analyse to user id.
	 *
	 * @param analysis the analysis
	 * @return the user id
	 */
	public UserId userAnalyseToUserId(AnalysisResult analysis) {
		UserId userId = new UserId();

		HolderDetail holderDetail = analysis.getHolderDetail();
		userId.setLastName(holderDetail.getLastName().stream().findFirst().orElse(StringUtils.EMPTY));
		userId.setFirstName(holderDetail.getFirstName().stream().findFirst().orElse(StringUtils.EMPTY));
		userId.setNationality(holderDetail.getNationality());

		Date birthDate = holderDetail.getBirthDate();
		int year = Integer.valueOf(StringUtils.defaultString(birthDate.getYear(), "1970"));
		int month = Integer.valueOf(StringUtils.defaultString(birthDate.getMonth(), "1"));
		int day = Integer.valueOf(StringUtils.defaultString(birthDate.getDay(), "1"));
		userId.setBirtDate(LocalDate.of(year, month, day));

		userId.setIdType(analysis.getDocumentClassification().getIdType());
		userId.setIdNumber(analysis.getDocumentDetail().getDocumentNumber());

		return userId;
	}

}
