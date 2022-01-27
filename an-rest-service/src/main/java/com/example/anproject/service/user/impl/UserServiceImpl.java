package com.example.anproject.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.anproject.service.ariadnext.idcheckio.IdCheckService;
import com.example.anproject.service.ariadnext.idcheckio.dto.AnalysisResult;
import com.example.anproject.service.user.UserService;
import com.example.anproject.service.user.bo.UserId;
import com.example.anproject.service.user.mapper.UserIdMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * The Class UserServiceImpl.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

	/** The id check service. */
	@Autowired
	private IdCheckService idCheckService;

	@Autowired
	private UserIdMapper userIdMapper;

	/**
	 * Validate user id.
	 *
	 * @param idImage the id image
	 * @return the analysis result
	 */
	@Override
	public UserId validateUserId(String idImage) {

		Integer credits = idCheckService.getUserRemainingCredits();
		log.info("Remaining credits : {}", credits);

		AnalysisResult analysis = idCheckService.analyseImage(false, idImage);

		UserId userId = userIdMapper.userAnalyseToUserId(analysis);

		userId.setIdValid(isIdValid(analysis));
		return userId;
	}

	/**
	 * Checks if is id valid.
	 *
	 * @return true, if is id valid
	 */
	private boolean isIdValid(AnalysisResult analysis) {
		// TODO Analyse CheckReportSummary content to check if the ID is valid
		return true;
	}

}
