package com.example.anproject.service.thirdparty.ariadnext.idcheckio.dto;

import java.util.List;

import lombok.Data;

/**
 * The holder detail.
 */
@Data
public class HolderDetail {

	/** The last name. */
	private List<String> lastName;

	/** The first name. */
	private List<String> firstName;

	/** The usage name. */
	private String usageName;

	/** The nationality. */
	private String nationality;

	/** The gender. */
	private String gender;

	/** The address. */
	private String address;

	/** The birth date. */
	private Date birthDate;

	/** The birth place. */
	private String birthPlace;

	/** The birth dpt. */
	private String birthDpt;

}
