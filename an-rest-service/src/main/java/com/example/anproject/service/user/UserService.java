package com.example.anproject.service.user;

import com.example.anproject.service.user.bo.UserId;

/**
 * The Interface UserService.
 */
public interface UserService {

	/**
	 * Validate a user image ID.
	 *
	 * @param image the image
	 * @return the UserId result
	 */
	UserId validateUserId(String image);

	/**
	 * Validate a user subscription.
	 *
	 * @param userName the user name
	 * @param userId   the user id
	 * @return true, if successful
	 */
	boolean validateUserSubscription(String userName, UserId userId);

}
