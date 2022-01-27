package com.example.anproject.rest;

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

	@GetMapping("/checkId")
	public UserId checkUserId() throws Exception {

		return userService.validateUserId(null);
	}

}
