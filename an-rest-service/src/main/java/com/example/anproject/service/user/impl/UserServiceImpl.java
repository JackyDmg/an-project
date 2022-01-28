package com.example.anproject.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.anproject.service.ariadnext.idcheckio.IdCheckService;
import com.example.anproject.service.exception.BadRequestException;
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
	 * @param image the image
	 * @return the analysis result
	 */
	@Override
	public UserId validateUserId(String image) {

		Integer credits = idCheckService.getUserRemainingCredits();
		log.info("Remaining credits : {}", credits);

		return idCheckService.analyseImage(false, image);
	}

	/**
	 * Validate user subscription.
	 *
	 * @param userName the user name
	 * @param userId   the user id
	 * @return true, if successful
	 */
	@Override
	public boolean validateUserSubscription(String userName, UserId userId) {

		if (userId.isIdValid() && idMatchUserName(userName, userId)) {
			// TODO compare userId to the authenticated user information to check if it's
			// match. If it matches :
			// - Validate the subscription
			// - Store User information subscription in database
			return true;
		}
		throw new BadRequestException(
				"Le nom d'utilisateur [" + userName + "] ne correspond pas à la carte d'identité !");
	}

	/**
	 * Id match user name.
	 *
	 * @param userName the user name
	 * @param userId   the user id
	 * @return true, if successful
	 */
	private boolean idMatchUserName(String userName, UserId userId) {

		// Compare userName with userId ( !! userName from social network are not always
		// the real one !!
		return true;
	}

}
