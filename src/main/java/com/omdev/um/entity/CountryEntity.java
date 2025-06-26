package com.omdev.um.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Table(name = "country_tbl")
@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class CountryEntity {
	
	public CountryEntity(){
		
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "country_id")
	@NonNull
	private Integer countryId;
	@Column(name = "country_name")
	@NonNull
	private String countryName;
	
}
