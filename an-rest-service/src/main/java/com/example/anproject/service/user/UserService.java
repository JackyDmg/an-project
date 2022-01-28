package com.example.anproject.service.user;

import com.example.anproject.service.user.bo.UserId;

/**
 * The Interface UserService.
 */
public interface UserService {

	/**
	 * Validate user id.
	 *
	 * @param idImage the id image
	 * @return the UserId result
	 */
	UserId validateUserId(String idImage);

	/**
	 * Validate user subscription.
	 *
	 * @param userId the user id
	 * @return true, if successful
	 */
	boolean validateUserSubscription(UserId userId);

}
