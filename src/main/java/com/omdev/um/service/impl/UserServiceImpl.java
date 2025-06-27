package com.omdev.um.service.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.omdev.um.constants.UserException;
import com.omdev.um.dto.LoginFormDTO;
import com.omdev.um.dto.RegisterFormDTO;
import com.omdev.um.dto.ResetPasswordFormDTO;
import com.omdev.um.dto.UserDTO;
import com.omdev.um.entity.CityEntity;
import com.omdev.um.entity.CountryEntity;
import com.omdev.um.entity.StateEntity;
import com.omdev.um.entity.UserEntity;
import com.omdev.um.repo.CityRepository;
import com.omdev.um.repo.CountryRepository;
import com.omdev.um.repo.StateRepository;
import com.omdev.um.repo.UserRepository;
import com.omdev.um.service.EmailService;
import com.omdev.um.service.UserService;
import com.omdev.um.utils.CreateUserDTOHelper;
import com.omdev.um.utils.MapDTOHelper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final CountryRepository countryRepository;
	private final CityRepository cityRepository;
	private final StateRepository stateRepository;
	
	private final CreateUserDTOHelper userDTOHelper;
	private final MapDTOHelper mapDTOHelper;
	
	private final EmailService emailService;
	

	@Override
	public Map<Integer, String> getCountries() {
		log.info("getCountries called");
		List<CountryEntity> countryList = countryRepository.findAll();
		Map<Integer, String> map = countryList.stream()
											  .collect(Collectors.toMap(CountryEntity::getCountryId, CountryEntity::getCountryName));
		log.info("retruning map -- countryMap : {}",map.toString());
		return map;
	}

	@Override
	public Map<Integer, String> getStates(Integer countryId) {
		log.info("getStates called -- countryId : {}",countryId);
		List<StateEntity> stateList = stateRepository.findByCountryId(countryId);
		Map<Integer, String> map = stateList.stream()
											  .collect(Collectors.toMap(StateEntity::getStateId, StateEntity::getStateName));
		log.info("retruning map -- stateMap : {}",map.toString());
		return map;
	}

	@Override
	public Map<Integer, String> getCities(Integer stateId) {
		log.info("getCities called -- stateId : {}",stateId);
		List<CityEntity> cityList = cityRepository.findByStateId(stateId);
		Map<Integer, String> map = cityList.stream()
											  .collect(Collectors.toMap(CityEntity::getCityId, CityEntity::getCityName));
		log.info("retruning map -- cityMap : {}",map.toString());
		return map;
	}

	@Override	
	public boolean duplicateEmailCheck(String email) {
		log.info("duplicateEmailCheck -- email : {}",email);
		UserEntity byEmail = userRepository.findByEmail(email);
		log.info("byEmail:{}",byEmail);
		return byEmail == null;
	}

	@Override
	public boolean registerUser(RegisterFormDTO regFormDTO) throws UserException {
		log.info("registerUser called with RegisterFormDTO argument");
		//Mapping RegisterFormDTO to UserEntity
		UserEntity userEntity = mapDTOHelper.mapRegisterFormToUserEntity(regFormDTO);
		//perform save operation
		UserEntity savedUser = userRepository.save(userEntity);
		if(savedUser.getId() > 0) {
			log.info("Registration Successfull -- userId : {}",savedUser.getId());
			String subject = "Your Account Created";
			String body = "Your Password To Login : "+savedUser.getPassword();
			String to = regFormDTO.getEmail();
			
			emailService.sendEmail(subject, body, to);
			return true;
		}
		log.info("Registration Failed");
		return false;
	}

	@Override
	public UserDTO login(LoginFormDTO loginFormDTO) {
		log.info("login -- LoginFormDTO : {}",loginFormDTO);
		UserEntity byEmailAndPassword = userRepository.findByEmailAndPassword
							(loginFormDTO.getEmail(), loginFormDTO.getPassword());
		
		if(byEmailAndPassword==null) {
			return null;
		}
		//calling helper class
		UserDTO userDTO = userDTOHelper.createUserDTO(byEmailAndPassword); 
		
		log.info("login returning userDTO : {}",userDTO);
		return userDTO;
	}

	@Override
	public boolean resetPassword(ResetPasswordFormDTO resetPwdFormDTO) throws UserException {
		log.info("resetPassword called -- resetPwdFormDTO userId: {}",resetPwdFormDTO.getId());
		UserEntity userEntity = userRepository.findById(resetPwdFormDTO.getId()).orElse(null);
		if(userEntity == null) {
			throw new UserException("User Not Found ...");
		}
		if(! userEntity.getPassword().equals(resetPwdFormDTO.getOldPassword())){
			throw new UserException("Old Password Did Not Match");
		}
		boolean passwordResetStatus = userEntity.isPasswordReset();
		if(!passwordResetStatus) {
			//set passwordResetStatus true
			log.info("It is first occurence of password reset");
			userEntity.setPasswordReset(true);
		}
		userEntity.setPassword(resetPwdFormDTO.getNewPassword());
		
		UserEntity updatedUser =  userRepository.save(userEntity); //Upsert Operation
		if(updatedUser.getPassword() == resetPwdFormDTO.getNewPassword()) {
			log.info("password updated successfully");
			return true;
		}
		log.info("password update failed");
		return false;
	}

	@Override
	public UserDTO findUserById(Integer userId) {
		log.info("findUserById-- userId: {}",userId);
		UserEntity userDetail = userRepository.findById(userId).orElse(null);
		//calling helper class
		UserDTO userDTO = userDTOHelper.createUserDTO(userDetail); 
		log.info("findUserById-- UserDTO:{}",userDTO);
		return userDTO;
	}
}
