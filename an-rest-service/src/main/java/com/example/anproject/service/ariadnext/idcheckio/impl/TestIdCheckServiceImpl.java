package com.example.anproject.service.ariadnext.idcheckio.impl;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.example.anproject.service.ariadnext.exception.AccessDeniedException;
import com.example.anproject.service.ariadnext.exception.BadRequestException;
import com.example.anproject.service.ariadnext.exception.GenericException;
import com.example.anproject.service.ariadnext.exception.InternalErrorException;
import com.example.anproject.service.ariadnext.exception.NoAuthenticationFoundException;
import com.example.anproject.service.ariadnext.idcheckio.AriadNextConstant;
import com.example.anproject.service.ariadnext.idcheckio.IdCheckService;
import com.example.anproject.service.ariadnext.idcheckio.client.TestIdCheckIoClient;
import com.example.anproject.service.ariadnext.idcheckio.dto.AnalysisResult;
import com.example.anproject.service.ariadnext.idcheckio.dto.ImageAnalysis;
import com.example.anproject.service.ariadnext.idcheckio.dto.UserResponse;

import feign.FeignException;

@Service
@Qualifier("testIdCheckService")
@ConditionalOnProperty(name = "ariadnext.idcheckio.client.plateform", havingValue = AriadNextConstant.PLATEFORM_TEST)
public class TestIdCheckServiceImpl implements IdCheckService {

	/** The id check io client. */
	@Autowired
	private TestIdCheckIoClient idCheckIoClient;

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
	 * Analyse image.
	 *
	 * @param asyncMode the async mode
	 * @param image     the image
	 * @return the analysis result
	 */
	@Override
	public AnalysisResult analyseImage(boolean asyncMode, String image) {
		try {

			ImageAnalysis imageAnalysisDto = new ImageAnalysis();
			imageAnalysisDto.setFrontImage(image);

			// TODO : Manage async mode
			return idCheckIoClient.analyseImage(asyncMode, imageAnalysisDto);

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