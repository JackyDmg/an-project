package com.example.anproject.rest;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.anproject.service.user.UserService;
import com.example.anproject.service.user.bo.UserId;

@RestController
@RequestMapping("/")
public class MainController {

	/** The idCheckIo service. */
	@Autowired
	private UserService userService;

	/**
	 * Suscribe service.
	 *
	 * @return the boolean
	 * @throws Exception the exception
	 */
	@GetMapping("/suscribe")
	public Boolean suscribeService() throws Exception {

		String encodedString = getLocalImageIdForTest();

		UserId userId = userService.validateUserId(encodedString);
		return userService.validateUserSubscription(userId);
	}

	private String getLocalImageIdForTest() throws IOException {
		// Get image from local resource
		ClassLoader classLoader = getClass().getClassLoader();
		File inputFile = new File(classLoader.getResource("CarteIdentite.jpg").getFile());
		byte[] fileContent = FileUtils.readFileToByteArray(inputFile);
		String encodedString = Base64.getEncoder().encodeToString(fileContent);
		return encodedString;
	}

}
