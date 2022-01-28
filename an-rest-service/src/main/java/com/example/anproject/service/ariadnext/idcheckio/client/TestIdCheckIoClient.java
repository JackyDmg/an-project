package com.example.anproject.service.ariadnext.idcheckio.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.anproject.service.ariadnext.idcheckio.AriadNextConstant;
import com.example.anproject.service.ariadnext.idcheckio.dto.AnalysisResult;
import com.example.anproject.service.ariadnext.idcheckio.dto.ImageAnalysis;
import com.example.anproject.service.ariadnext.idcheckio.dto.UserResponse;

/**
 * The IdCheckIo Client interface
 */
@FeignClient(value = "test-idcheckio", url = AriadNextConstant.URL_TEST, configuration = IdCheckIoConfiguration.class)
public interface TestIdCheckIoClient {

	/**
	 * Gets the user information.
	 *
	 * @return the user information
	 */
	@GetMapping("/v0/admin/user")
	UserResponse getUserInformation();

	/**
	 * Analyse image.
	 *
	 * @param asyncMode     the async mode
	 * @param imageAnalysis the image analysis
	 * @return the analysis result dto
	 */
	@PostMapping("/v0/task/image")
	AnalysisResult analyseImage(@RequestParam("asyncMode") boolean asyncMode, //
			@RequestBody ImageAnalysis imageAnalysis);

}
