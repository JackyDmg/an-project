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
	 * Analyse image.
	 *
	 * @param image the image
	 * @return the analysis result dto
	 */
	UserId analyseImage(String image);

}
