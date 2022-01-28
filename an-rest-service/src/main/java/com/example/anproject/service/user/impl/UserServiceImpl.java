package com.example.anproject.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.anproject.service.ariadnext.idcheckio.IdCheckService;
import com.example.anproject.service.user.UserService;
import com.example.anproject.service.user.bo.UserId;

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

		return idCheckService.analyseImage(false, idImage);
	}

	/**
	 * Validate user subscription.
	 *
	 * @param userId the user id
	 * @return true, if successful
	 */
	@Override
	public boolean validateUserSubscription(UserId userId) {

		if (userId.isIdValid()) {
			// TODO compare userId to the authenticated user information to check if it's
			// match. If it matches :
			// - Validate the subscription
			// - Store User information subscription in database
			return true;
		}
		return false;
	}

}
