package com.omdev.um.dto;

import lombok.Data;

@Data
public class UserDTO {
	 private Integer id;
	 private String name;
	 private String email;
	 private String phone;
	 private String password;
	 private boolean passwordStatus;
	 private String country;
	 private String state;
	 private String city;
	 
	 
}
