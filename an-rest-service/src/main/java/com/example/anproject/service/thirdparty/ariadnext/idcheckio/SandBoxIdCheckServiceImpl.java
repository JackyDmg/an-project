package com.example.anproject.service.thirdparty.ariadnext.idcheckio;

import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.example.anproject.service.exception.AccessDeniedException;
import com.example.anproject.service.exception.BadRequestException;
import com.example.anproject.service.exception.GenericException;
import com.example.anproject.service.exception.InternalErrorException;
import com.example.anproject.service.exception.NoAuthenticationFoundException;
import com.example.anproject.service.thirdparty.IdCheckService;
import com.example.anproject.service.thirdparty.ariadnext.idcheckio.client.SandBoxIdCheckIoClient;
import com.example.anproject.service.thirdparty.ariadnext.idcheckio.dto.AnalysisResult;
import com.example.anproject.service.thirdparty.ariadnext.idcheckio.dto.Image;
import com.example.anproject.service.thirdparty.ariadnext.idcheckio.dto.ImageAnalysis;
import com.example.anproject.service.thirdparty.ariadnext.idcheckio.dto.ImagesList;
import com.example.anproject.service.thirdparty.ariadnext.idcheckio.dto.UserResponse;
import com.example.anproject.service.user.bo.UserId;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

/**
 * The IdCheckService implementation for Test environment.
 */
@Service
@Slf4j
@Qualifier("sandBoxIdCheckService")
@ConditionalOnProperty(name = "ariadnext.idcheckio.client.plateform", havingValue = AriadNextConstant.PLATEFORM_SANDBOX)
public class SandBoxIdCheckServiceImpl implements IdCheckService {

	/** The Sandbox id check io client. */
	@Autowired
	private SandBoxIdCheckIoClient idCheckIoClient;

	/** The user id mapper. */
	@Autowired
	private IdCheckMapper userIdMapper;

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

	@Override
	public UserId analyseImageId(String image) {
		try {

			ImageAnalysis imageAnalysis = new ImageAnalysis();
			imageAnalysis.setFrontImage(getDemoImageFromSandbox());

			// TODO : Manage async mode
			AnalysisResult analysis = idCheckIoClient.analyseImageSync(imageAnalysis);

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
	 * Checks if Image content analysis is valid
	 *
	 * @return true, if the id valid
	 */
	private boolean isIdValid(AnalysisResult analysis) {
		return true;
	}

}
