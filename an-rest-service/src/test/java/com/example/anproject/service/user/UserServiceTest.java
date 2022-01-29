package com.example.anproject.service.user;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.anproject.service.exception.BadRequestException;
import com.example.anproject.service.exception.NotAllowedException;
import com.example.anproject.service.thirdparty.IdCheckService;
import com.example.anproject.service.user.bo.UserId;
import com.example.anproject.service.user.impl.UserServiceImpl;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {

	@InjectMocks
	private UserService userService = new UserServiceImpl();

	@Mock
	private IdCheckService idCheckServiceMock;

	@Before
	public void before() {

	}

	@Test
	public void testValidateUserId() {

		String imageToTest = "image";

		when(idCheckServiceMock.getUserRemainingCredits()).thenReturn(200);
		UserId result = new UserId();
		result.setFirstName("test");
		result.setIdValid(true);
		when(idCheckServiceMock.analyseImage(imageToTest)).thenReturn(result);

		UserId userId = userService.validateUserId(imageToTest);

		assertNotNull(userId);

	}

	@Test(expected = NotAllowedException.class)
	public void testValidateUserId_notCredit() {

		String imageToTest = "image";

		when(idCheckServiceMock.getUserRemainingCredits()).thenReturn(0);

		UserId userId = userService.validateUserId(imageToTest);

		assertNull(userId);
	}

	@Test(expected = BadRequestException.class)
	public void testValidateUserId_analysisFailed() {

		String imageToTest = "image";

		when(idCheckServiceMock.getUserRemainingCredits()).thenReturn(200);
		UserId result = new UserId();
		result.setFirstName("test");
		result.setIdValid(true);
		when(idCheckServiceMock.analyseImage(imageToTest)).thenThrow(new BadRequestException());

		UserId userId = userService.validateUserId(imageToTest);

		assertNull(userId);
	}

	@Test
	public void validateUserSubscription_valid() {

		UserId userId = new UserId();
		userId.setFirstName("test");
		userId.setIdValid(true);

		boolean resul = userService.validateUserSubscription("userName", userId);

		assertTrue(resul);
	}

	@Test(expected = BadRequestException.class)
	public void validateUserSubscription_notValid() {

		UserId userId = new UserId();
		userId.setFirstName("test");
		userId.setIdValid(false);

		boolean resul = userService.validateUserSubscription("userName", userId);

		assertFalse(resul);
	}

}
