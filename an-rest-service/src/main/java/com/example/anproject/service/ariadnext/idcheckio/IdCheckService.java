package com.example.anproject.service.ariadnext.idcheckio;

import com.example.anproject.service.ariadnext.idcheckio.dto.AnalysisResult;
import com.example.anproject.service.ariadnext.idcheckio.dto.ImagesList;

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
	 * Gets the images list.
	 *
	 * @return the images list
	 */
	ImagesList getImagesList();

	/**
	 * Gets the image.
	 *
	 * @param imageUid the image uid
	 * @param rawType  the raw type
	 * @param face     the face
	 * @param light    the light
	 * @return the image
	 */
	String getImage(String imageUid, String rawType, String face, String light);

	/**
	 * Analyse image.
	 *
	 * @param asyncMode the async mode
	 * @param image     the image
	 * @return the analysis result dto
	 */
	AnalysisResult analyseImage(boolean asyncMode, String image);

}
