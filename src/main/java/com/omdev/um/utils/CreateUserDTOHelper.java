package com.omdev.um.utils;

import org.springframework.stereotype.Component;

import com.omdev.um.constants.CityEnum;
import com.omdev.um.constants.CountryEnum;
import com.omdev.um.constants.StateEnum;
import com.omdev.um.dto.UserDTO;
import com.omdev.um.entity.UserEntity;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CreateUserDTOHelper {
	
	public UserDTO createUserDTO(UserEntity userEntity) {
		log.info("createUserDTO called -- userEntity Id : {}",userEntity.getId());
		
		UserDTO userDTO = new UserDTO();
		userDTO.setId(userEntity.getId());
		userDTO.setName(userEntity.getName());
		userDTO.setEmail(userEntity.getEmail());
		userDTO.setPassword(userEntity.getPassword());
		userDTO.setPhone(userEntity.getPhone());
		userDTO.setPassword_status(userEntity.isPasswordReset());
		
		CountryEnum countryEnum = CountryEnum.getEnumById(userEntity.getCountry().getCountryId());
		StateEnum stateEnum  = StateEnum.getEnumById(userEntity.getState().getStateId());
		CityEnum cityEnum  = CityEnum.getEnumById(userEntity.getCity().getCityId());
		userDTO.setCountry(countryEnum.getName());
		userDTO.setState(stateEnum.getName());
		userDTO.setCity(cityEnum.getName());
		 
		log.info("returning city : {}, state : {} , country : {}",userDTO.getCity(),userDTO.getState(),userDTO.getCountry());
		return userDTO;
	
	}
}
