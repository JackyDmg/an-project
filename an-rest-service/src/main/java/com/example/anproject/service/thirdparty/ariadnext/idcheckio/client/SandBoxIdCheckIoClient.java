package com.example.anproject.service.thirdparty.ariadnext.idcheckio.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.anproject.service.thirdparty.ariadnext.idcheckio.AriadNextConstant;
import com.example.anproject.service.thirdparty.ariadnext.idcheckio.dto.AnalysisResult;
import com.example.anproject.service.thirdparty.ariadnext.idcheckio.dto.ImageAnalysis;
import com.example.anproject.service.thirdparty.ariadnext.idcheckio.dto.ImagesList;
import com.example.anproject.service.thirdparty.ariadnext.idcheckio.dto.TaskDetail;
import com.example.anproject.service.thirdparty.ariadnext.idcheckio.dto.UserResponse;

/**
 * The IdCheckIo Client interface for SandBox environment.
 */
@FeignClient(value = "sandbox-idcheckio", url = AriadNextConstant.URL_SANDBOX, configuration = IdCheckIoConfiguration.class)
public interface SandBoxIdCheckIoClient {

	/**
	 * Gets the user information.
	 *
	 * @return the user information
	 */
	@GetMapping("/v0/admin/user")
	UserResponse getUserInformation();

	/**
	 * Gets the mrz list.
	 *
	 * @return the mrz list
	 */
	@GetMapping("/v0/sandbox/imagelist")
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
	@GetMapping("/v0/sandbox/image/{imageUid}")
	String getImage(@PathVariable("imageUid") String imageUid, //
			@RequestParam("rawType") String rawType, //
			@RequestParam("face") String face, //
			@RequestParam("light") String light);

	/**
	 * Analyze image with synchronized result.
	 *
	 * @param imageAnalysis the image analysis
	 * @return the analysis result dto
	 */
	@PostMapping("/v0/task/image?asyncMode=false")
	AnalysisResult analyseImageSync(@RequestBody ImageAnalysis imageAnalysis);

	/**
	 * Analyze image without synchronized result.
	 *
	 * @param imageAnalysis the image analysis
	 * @return the task detail
	 */
	@PostMapping("/v0/task/image?asyncMode=true")
	TaskDetail analyseImageAsync(@RequestBody ImageAnalysis imageAnalysis);

	/**
	 * Gets the task status.
	 *
	 * @param uid  the uid
	 * @param wait the wait
	 * @return the task status
	 */
	@GetMapping("/v0/task/{uid}")
	TaskDetail getTaskStatus(@PathVariable("uid") String uid, //
			@RequestParam("wait") long wait);

	/**
	 * Gets the analysis result.
	 *
	 * @param uid                   the uid
	 * @param rectoImageCropped     the recto image cropped
	 * @param signatureImageCropped the signature image cropped
	 * @param faceImageCropped      the face image cropped
	 * @return the analysis result
	 */
	@GetMapping("/v0/result/{uid}")
	AnalysisResult getAnalysisResult(@PathVariable("uid") String uid, //
			@RequestParam("rectoImageCropped") String rectoImageCropped, //
			@RequestParam("signatureImageCropped") String signatureImageCropped, //
			@RequestParam("faceImageCropped") String faceImageCropped);
}
