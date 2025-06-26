package com.omdev.um.constants;

import lombok.Getter;

@Getter
public enum CountryEnum {
	INDIA(1,"INDIA"),
	USA(2,"UNITED STATE AMERICA");
	CountryEnum(int id, String name) {
		this.id = id;
		this.name = name;
	}
	private Integer id;
	private String name;
	
	public static CountryEnum getEnumById(Integer id) {
		if(id == null) {
			return null;
		}
		for(CountryEnum country : CountryEnum.values()) {
			if(country.getId() == id) {
				return country;
			}
		}
		return null;
	}
	
	public static CountryEnum getEnumByName(String name) {
		if(name == null) {
			return null;
		}
		for(CountryEnum country : CountryEnum.values()) {
			if(country.getName().equals(name)) {
				return country;
			}
		}
		return null;
	}
}
