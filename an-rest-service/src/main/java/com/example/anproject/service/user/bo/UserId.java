package com.example.anproject.service.user.bo;

import java.time.LocalDate;

import lombok.Data;

/**
 * The user id.
 */
@Data
public class UserId {

	/** The last name. */
	private String lastName;

	/** The first name. */
	private String firstName;

	/** The nationality. */
	private String nationality;

	/** The birt date. */
	private LocalDate birtDate;

	/** The id type. */
	private String idType;

	/** The id type. */
	private String idNumber;

	/** The is id valid. */
	private boolean isIdValid;

}
