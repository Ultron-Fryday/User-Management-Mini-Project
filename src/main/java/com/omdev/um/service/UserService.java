package com.omdev.um.service;

import java.util.Map;

import com.omdev.um.dto.LoginFormDTO;
import com.omdev.um.dto.RegisterFormDTO;
import com.omdev.um.dto.ResetPasswordFormDTO;
import com.omdev.um.dto.UserDTO;

public interface UserService {
	public Map<Integer, String> getCountries();
	public Map<Integer, String> getStates(Integer countryId);
	public Map<Integer, String> getCities(Integer stateId);
	
	public boolean duplicateEmailCheck(String email);
	public boolean registerUser(RegisterFormDTO regFormDTO) throws Exception;
	public UserDTO login(LoginFormDTO loginFormDTO);
	
	public boolean resetPassword(ResetPasswordFormDTO resetPwdFormDTO) throws Exception;
	
	public UserDTO findUserById(Integer userId);
}
