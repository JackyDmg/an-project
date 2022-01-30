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
import com.example.anproject.service.thirdparty.ariadnext.idcheckio.client.TestIdCheckIoClient;
import com.example.anproject.service.thirdparty.ariadnext.idcheckio.dto.AnalysisResult;
import com.example.anproject.service.thirdparty.ariadnext.idcheckio.dto.ImageAnalysis;
import com.example.anproject.service.thirdparty.ariadnext.idcheckio.dto.UserResponse;
import com.example.anproject.service.user.bo.UserId;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;

/**
 * The IdCheckService implementation for Test environment.
 */
@Service
@Qualifier("testIdCheckService")
@ConditionalOnProperty(name = "ariadnext.idcheckio.client.plateform", havingValue = AriadNextConstant.PLATEFORM_TEST)
@Slf4j
public class TestIdCheckServiceImpl implements IdCheckService {

	/** The Test id check io client. */
	@Autowired
	private TestIdCheckIoClient idCheckIoClient;

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
			imageAnalysis.setFrontImage(image);

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
	 * Checks if Image content analysis is valid
	 *
	 * @return true, if the id valid
	 */
	private boolean isIdValid(AnalysisResult analysis) {
		// TODO Analyse CheckReportSummary content to check if the ID is valid

		analysis.getCheckReportSummary().getCheck().stream().forEach(verif -> {
			if (!verif.getResult().equals("OK")) {
				log.warn("Id is not valid - cause {}", verif.getResultMsg());
				throw new BadRequestException(verif.getResultMsg());
			}

		});

		return true;
	}
}
