package com.example.anproject.service.thirdparty;

import com.example.anproject.service.user.bo.UserId;

/**
 * The Interface IdCheckIoService.
 */
public interface IdCheckService {

	/**
	 * Gets the user remaining credits.
	 *
	 * @return the user remaining credits
	 */
	Integer getUserRemainingCredits();

	/**
	 * Analyse a identification image.
	 *
	 * @param image the image to analyse
	 * @return the user id extracted from the ID content
	 */
	UserId analyseImageId(String image);

}
