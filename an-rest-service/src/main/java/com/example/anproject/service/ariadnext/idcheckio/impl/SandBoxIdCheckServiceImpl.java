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
import com.example.anproject.service.ariadnext.idcheckio.client.SandBoxIdCheckIoClient;
import com.example.anproject.service.ariadnext.idcheckio.dto.AnalysisResult;
import com.example.anproject.service.ariadnext.idcheckio.dto.Image;
import com.example.anproject.service.ariadnext.idcheckio.dto.ImageAnalysis;
import com.example.anproject.service.ariadnext.idcheckio.dto.ImagesList;
import com.example.anproject.service.ariadnext.idcheckio.dto.UserResponse;
import com.example.anproject.service.ariadnext.idcheckio.mapper.IdCheckMapper;
import com.example.anproject.service.user.bo.UserId;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Qualifier("sandBoxIdCheckService")
@ConditionalOnProperty(name = "ariadnext.idcheckio.client.plateform", havingValue = AriadNextConstant.PLATEFORM_SANDBOX)
public class SandBoxIdCheckServiceImpl implements IdCheckService {

	/** The id check io client. */
	@Autowired
	private SandBoxIdCheckIoClient idCheckIoClient;

	@Autowired
	private IdCheckMapper userIdMapper;

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
	public UserId analyseImage(boolean asyncMode, String image) {
		try {

			ImageAnalysis imageAnalysisDto = new ImageAnalysis();
			imageAnalysisDto.setFrontImage(getDemoImageFromSandbox());

			// TODO : Manage async mode
			AnalysisResult analysis = idCheckIoClient.analyseImage(asyncMode, imageAnalysisDto);

			UserId userId = userIdMapper.userAnalyseToUserId(analysis);
			userId.setIdValid(isIdValid(analysis));

			return userId;

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
	 * Gets the demo image from sandbox.
	 *
	 * @return the demo image from sandbox
	 */
	private String getDemoImageFromSandbox() {

		try {
			ImagesList imagesList = idCheckIoClient.getImagesList();
			Image imageId = imagesList.getImages().get(4);
			log.info("List of Images available for test : {}", imageId);

			return idCheckIoClient.getImage(imageId.getDoc(), imageId.getRawType(), imageId.getFace(),
					imageId.getLight());
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

	/**
	 * Checks if is id valid.
	 *
	 * @return true, if is id valid
	 */
	private boolean isIdValid(AnalysisResult analysis) {
		// TODO Analyse CheckReportSummary content to check if the ID is valid
		return true;
	}

}
