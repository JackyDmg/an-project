package com.example.anproject.service.ariadnext.idcheckio.impl;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.anproject.service.ariadnext.exception.AccessDeniedException;
import com.example.anproject.service.ariadnext.exception.BadRequestException;
import com.example.anproject.service.ariadnext.exception.GenericException;
import com.example.anproject.service.ariadnext.exception.InternalErrorException;
import com.example.anproject.service.ariadnext.exception.NoAuthenticationFoundException;
import com.example.anproject.service.ariadnext.idcheckio.IdCheckService;
import com.example.anproject.service.ariadnext.idcheckio.client.IdCheckIoClient;
import com.example.anproject.service.ariadnext.idcheckio.dto.AnalysisResult;
import com.example.anproject.service.ariadnext.idcheckio.dto.ImageAnalysis;
import com.example.anproject.service.ariadnext.idcheckio.dto.ImagesList;
import com.example.anproject.service.ariadnext.idcheckio.dto.UserResponse;

import feign.FeignException;

@Service
public class IdCheckServiceImpl implements IdCheckService {

	/** The id check io client. */
	@Autowired
	private IdCheckIoClient idCheckIoClient;

	/**
	 * Gets the user remaining credits.
	 *
	 * @return the user remaining credits
	 */
	@Override
	public Integer getUserRemainingCredits() {
		try {

			UserResponse userInfor = idCheckIoClient.getUserInformation();
			return userInfor.getRemainingCredits();

		} catch (FeignException e) {
			switch (e.status()) {
			case HttpStatus.SC_BAD_REQUEST:
				throw new BadRequestException(e.getMessage());
			case HttpStatus.SC_UNAUTHORIZED:
				throw new NoAuthenticationFoundException(e.getMessage());
			case HttpStatus.SC_FORBIDDEN:
				throw new AccessDeniedException(e.getMessage());
			case HttpStatus.SC_INTERNAL_SERVER_ERROR:
				throw new InternalErrorException(e.getMessage());
			default:
				throw new GenericException(e.getMessage());
			}
		}
	}

	/**
	 * Gets the images list.
	 *
	 * @return the images list
	 */
	@Override
	public ImagesList getImagesList() {
		try {

			ImagesList imagesList = idCheckIoClient.getImagesList();
			return imagesList;

		} catch (FeignException e) {
			switch (e.status()) {
			case HttpStatus.SC_BAD_REQUEST:
				throw new BadRequestException(e.getMessage());
			case HttpStatus.SC_INTERNAL_SERVER_ERROR:
				throw new InternalErrorException(e.getMessage());
			default:
				throw new GenericException(e.getMessage());
			}
		}
	}

	/**
	 * Gets the image.
	 *
	 * @param imageUid the image uid
	 * @param rawType  the raw type
	 * @param face     the face
	 * @param light    the light
	 * @return the image
	 */
	@Override
	public String getImage(String imageUid, String rawType, String face, String light) {
		try {

			return idCheckIoClient.getImage(imageUid, rawType, face, light);

		} catch (FeignException e) {
			switch (e.status()) {
			case HttpStatus.SC_BAD_REQUEST:
			case HttpStatus.SC_NOT_FOUND:
				throw new BadRequestException(e.getMessage());
			case HttpStatus.SC_INTERNAL_SERVER_ERROR:
				throw new InternalErrorException(e.getMessage());
			default:
				throw new GenericException(e.getMessage());
			}
		}
	}

	@Override
	public AnalysisResult analyseImage(boolean asyncMode, String image) {
		try {

			ImageAnalysis imageAnalysisDto = new ImageAnalysis();
			imageAnalysisDto.setFrontImage(image);
			AnalysisResult analysis = idCheckIoClient.analyseImage(asyncMode, imageAnalysisDto);
			return analysis;

		} catch (FeignException e) {
			switch (e.status()) {
			case HttpStatus.SC_BAD_REQUEST:
				throw new BadRequestException(e.getMessage());
			case HttpStatus.SC_UNAUTHORIZED:
				throw new NoAuthenticationFoundException(e.getMessage());
			case HttpStatus.SC_FORBIDDEN:
				throw new AccessDeniedException(e.getMessage());
			case HttpStatus.SC_INTERNAL_SERVER_ERROR:
				throw new InternalErrorException(e.getMessage());
			default:
				throw new GenericException(e.getMessage());
			}
		}
	}

}
