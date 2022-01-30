package com.example.anproject.service.user.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.anproject.service.exception.BadRequestException;
import com.example.anproject.service.exception.NotAllowedException;
import com.example.anproject.service.thirdparty.IdCheckService;
import com.example.anproject.service.user.UserService;
import com.example.anproject.service.user.bo.UserId;

import lombok.extern.slf4j.Slf4j;

/**
 * The UserService implementation.
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

	/** The id check service. */
	@Autowired
	private IdCheckService idCheckService;

	@Override
	public UserId validateUserId(String image) {

		Integer credits = idCheckService.getUserRemainingCredits();
		log.info("Remaining credits : {}", credits);
		if (credits > 0)
			return idCheckService.analyseImageId(image);

		throw new NotAllowedException("Not enough crdits !");
	}

	@Override
	public boolean validateUserSubscription(String userName, UserId userId) {

		if (userId.isIdValid() && doesIdMatchesUser(userName, userId)) {
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
	 * Check if Id matches user information.
	 *
	 * @param userName the user name
	 * @param userId   the user id
	 * @return true, if successful
	 */
	private boolean doesIdMatchesUser(String userName, UserId userId) {

		// Compare userName with userId ( !! userName from social network are not always
		// the real one !!
		return true;
	}

}
