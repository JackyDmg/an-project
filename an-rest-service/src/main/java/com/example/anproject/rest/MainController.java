package com.example.anproject.rest;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.anproject.service.user.UserService;
import com.example.anproject.service.user.bo.UserId;

import lombok.extern.slf4j.Slf4j;

/**
 * The Main Controller.
 */
@RestController
@RequestMapping("/")
@Slf4j
public class MainController {

	/** The user service. */
	@Autowired
	private UserService userService;

	@RequestMapping("/user")
	public Map<String, Object> user(@AuthenticationPrincipal OAuth2User principal) {
		return Collections.singletonMap("name", principal.getAttribute("name"));
	}

	/**
	 * Subscribe service.
	 *
	 * @return the boolean
	 * @throws Exception the exception
	 */
	@PostMapping("/subscribe")
	public boolean subscribeService(@AuthenticationPrincipal OAuth2User principal) {

		// TODO modifier l'API pour fournir les éléments de sousscritpion (Nom, Prénom,
		// Date de naissance, Scan de la pièce d'identité)

		try {
			String image = getLocalImageIdForTest();
			UserId userId = userService.validateUserId(image);

			String userName = principal.getAttribute("name");
			if (userService.validateUserSubscription(userName, userId)) {
				String msg = "Hi, " + userId.getFirstName() + " " + userId.getLastName()
						+ ", your subscription has been granted !";
				log.info(msg);
				return true;
			}

		} catch (Exception e) {
			String msg = "Subscription refused because of " + e.getMessage();
			log.error(msg);
		}

		return false;
	}

	/**
	 * (For Demo) get local image to check
	 *
	 * @return the local image id for test
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	private String getLocalImageIdForTest() throws IOException {
		// Get image from local resource
		ClassLoader classLoader = getClass().getClassLoader();
		File inputFile = new File(classLoader.getResource("CarteIdentite.jpg").getFile());
		byte[] fileContent = FileUtils.readFileToByteArray(inputFile);
		String encodedString = Base64.getEncoder().encodeToString(fileContent);
		return encodedString;
	}

}
