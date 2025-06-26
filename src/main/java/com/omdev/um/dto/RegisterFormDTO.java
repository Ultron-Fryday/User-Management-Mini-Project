package com.omdev.um.dto;

import lombok.Data;

@Data
public class RegisterFormDTO {
	
	private String name;
	private String email;
	private String phno;
	private Integer countryId;
	private Integer stateId;
	private Integer cityId;
	
}
