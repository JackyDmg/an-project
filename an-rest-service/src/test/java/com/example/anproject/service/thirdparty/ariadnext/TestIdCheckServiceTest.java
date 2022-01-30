package com.example.anproject.service.thirdparty.ariadnext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.anproject.service.exception.BadRequestException;
import com.example.anproject.service.exception.NoAuthenticationFoundException;
import com.example.anproject.service.thirdparty.IdCheckService;
import com.example.anproject.service.thirdparty.ariadnext.idcheckio.IdCheckMapper;
import com.example.anproject.service.thirdparty.ariadnext.idcheckio.TestIdCheckServiceImpl;
import com.example.anproject.service.thirdparty.ariadnext.idcheckio.client.TestIdCheckIoClient;
import com.example.anproject.service.thirdparty.ariadnext.idcheckio.dto.AnalysisResult;
import com.example.anproject.service.thirdparty.ariadnext.idcheckio.dto.CheckReportSummary;
import com.example.anproject.service.thirdparty.ariadnext.idcheckio.dto.ImageAnalysis;
import com.example.anproject.service.thirdparty.ariadnext.idcheckio.dto.SingleVerification;
import com.example.anproject.service.thirdparty.ariadnext.idcheckio.dto.UserResponse;
import com.example.anproject.service.user.bo.UserId;

import feign.FeignException.FeignServerException;
import feign.Request;
import feign.Request.HttpMethod;

@RunWith(SpringJUnit4ClassRunner.class)
public class TestIdCheckServiceTest {

	@InjectMocks
	private IdCheckService idCheckService = new TestIdCheckServiceImpl();

	@Mock
	private TestIdCheckIoClient idCheckIoClientMock;

	@Spy
	private IdCheckMapper userIdMapper = new IdCheckMapper();

	@Before
	public void before() {

	}

	@Test
	public void testGetUserRemainingCredits() {

		UserResponse userResponse = InitTestData.buildUserResponse(200);
		when(idCheckIoClientMock.getUserInformation()).thenReturn(userResponse);

		int result = idCheckService.getUserRemainingCredits();

		assertEquals(200, result);

	}

	@Test(expected = BadRequestException.class)
	public void testGetUserRemainingCredits_BadRequestException() {

		Map<String, Collection<String>> headers = new HashMap<>();
		Request req = Request.create(HttpMethod.GET, "entreprise", headers, null, null, null);
		when(idCheckIoClientMock.getUserInformation())
				.thenThrow(new FeignServerException(HttpStatus.BAD_REQUEST.value(), "", req, null, headers));

		Integer result = idCheckService.getUserRemainingCredits();

		assertNull(result);

	}

	@Test(expected = NoAuthenticationFoundException.class)
	public void testGetUserRemainingCredits_NoAuthenticationFoundException() {

		Map<String, Collection<String>> headers = new HashMap<>();
		Request req = Request.create(HttpMethod.GET, "entreprise", headers, null, null, null);
		when(idCheckIoClientMock.getUserInformation())
				.thenThrow(new FeignServerException(HttpStatus.UNAUTHORIZED.value(), "", req, null, headers));

		Integer result = idCheckService.getUserRemainingCredits();

		assertNull(result);
	}

	// TODO : testGetUserRemainingCredits with other FeignException

	@Test
	public void testAnalyseImage_valid() {

		AnalysisResult analysis = InitTestData.buidlAnalysisResult();
		SingleVerification singleVerif = new SingleVerification();
		singleVerif.setIdentifier("id");
		singleVerif.setResult("OK");
		List<SingleVerification> check = new ArrayList<>();
		check.add(singleVerif);
		CheckReportSummary checkSummary = new CheckReportSummary();
		checkSummary.setCheck(check);
		analysis.setCheckReportSummary(checkSummary);

		when(idCheckIoClientMock.analyseImageSync(Mockito.any(ImageAnalysis.class))).thenReturn(analysis);

		UserId result = idCheckService.analyseImageId("image");

		assertNotNull(result);
		assertEquals("John", result.getFirstName());
		assertEquals("Doe", result.getLastName());
		assertTrue(result.isIdValid());
	}

	@Test(expected = BadRequestException.class)
	public void testAnalyseImage_notValid() {

		AnalysisResult analysis = InitTestData.buidlAnalysisResult();
		SingleVerification singleVerif = new SingleVerification();
		singleVerif.setIdentifier("id");
		singleVerif.setResult("ERROR");
		singleVerif.setResultMsg("Not valid");
		List<SingleVerification> check = new ArrayList<>();
		check.add(singleVerif);
		CheckReportSummary checkSummary = new CheckReportSummary();
		checkSummary.setCheck(check);
		analysis.setCheckReportSummary(checkSummary);

		when(idCheckIoClientMock.analyseImageSync(Mockito.any(ImageAnalysis.class))).thenReturn(analysis);

		UserId result = idCheckService.analyseImageId("image");

		assertNull(result);
	}

	@Test(expected = BadRequestException.class)
	public void testAnalyseImage_BadRequestException() {

		Map<String, Collection<String>> headers = new HashMap<>();
		Request req = Request.create(HttpMethod.GET, "entreprise", headers, null, null, null);
		when(idCheckIoClientMock.getUserInformation())
				.thenThrow(new FeignServerException(HttpStatus.BAD_REQUEST.value(), "", req, null, headers));
		when(idCheckIoClientMock.analyseImageSync(Mockito.any(ImageAnalysis.class)))
				.thenThrow(new FeignServerException(HttpStatus.BAD_REQUEST.value(), "", req, null, headers));

		UserId result = idCheckService.analyseImageId("image");

		assertNull(result);
	}

	// TODO : testGetUserRemainingCredits with other FeignException

}
