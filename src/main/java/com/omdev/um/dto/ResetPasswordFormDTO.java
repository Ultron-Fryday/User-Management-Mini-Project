package com.omdev.um.dto;

import lombok.Data;

@Data
public class ResetPasswordFormDTO {
	
	private Integer id;
	private String email;
	private String oldPassword;
	private String newPassword;
	private String confirmPassword;
}
