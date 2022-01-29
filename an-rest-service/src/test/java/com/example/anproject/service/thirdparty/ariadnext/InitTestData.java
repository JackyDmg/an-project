package com.example.anproject.service.thirdparty.ariadnext;

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
}
