package com.omdev.um.utils;

import org.springframework.stereotype.Component;

import com.omdev.um.constants.UserException;
import com.omdev.um.dto.RegisterFormDTO;
import com.omdev.um.entity.CityEntity;
import com.omdev.um.entity.CountryEntity;
import com.omdev.um.entity.StateEntity;
import com.omdev.um.entity.UserEntity;
import com.omdev.um.repo.CityRepository;
import com.omdev.um.repo.CountryRepository;
import com.omdev.um.repo.StateRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class MapDTOHelper {
	
	private final CountryRepository countryRepository;
	private final StateRepository stateRepository;
	private final CityRepository cityRepository;
	
	public UserEntity mapRegisterFormToUserEntity(RegisterFormDTO formDTO) throws UserException {
		log.info("mapRegisterFormToUserEntity -- formDTO email : {}",formDTO.getEmail());
		UserEntity userEntity = new UserEntity();
		userEntity.setName(formDTO.getName());
		userEntity.setEmail(formDTO.getEmail());
		userEntity.setPhone(formDTO.getPhno());
		
		//find CountryEntity,StateEntity,CityEntity by id
		CountryEntity countryEntity = countryRepository.findById(formDTO.getCountryId()).orElse(null);
		if(countryEntity ==  null ) {
			throw new UserException("CountryEntity not found");
		}
		StateEntity stateEntity = stateRepository.findById(formDTO.getStateId()).orElse(null);
		if(stateEntity ==  null ) {
			throw new UserException("StateEntity not found");
		}
		CityEntity cityEntity = cityRepository.findById(formDTO.getCityId()).orElse(null);
		if(cityEntity ==  null ) {
			throw new UserException("CityEntity not found");
		}
		//Perform Association
		userEntity.setCountry(countryEntity);
		userEntity.setState(stateEntity);
		userEntity.setCity(cityEntity);
		
		//Generate Random Password and map it
		String randomPassword = PasswordGenerator.generatePassword();
		userEntity.setPassword(randomPassword);
		
		//Set the password_status value false
		userEntity.setPasswordReset(false);
		log.info("mapRegisterFormToUserEntity -- returning userEntity ");
		return userEntity;
	}

	
	
	
}
