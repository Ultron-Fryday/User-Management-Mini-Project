package com.omdev.um.constants;

import lombok.Getter;

@Getter
public enum StateEnum {
	TELANGANA(1, "Telangana", 1), MAHARASHTRA(2, "Maharashtra", 1), ANDRAPRADESH(3, "AndraPradesh", 1),
	CALIFORNIA(4, "California", 2), FLORIDA(5, "Florida", 2);

	StateEnum(int id, String name, int countryId) {
		this.id = id;
		this.name = name;
		this.countryId = countryId;
	}

	private Integer id;
	private String name;
	private Integer countryId;
	
	public static StateEnum getEnumById(Integer id) {
		if(id == null) {
			return null;
		}
		for(StateEnum state : StateEnum.values()) {
			if(state.getId() == id) {
				return state;
			}
		}
		return null;
	}
	
	public static StateEnum getEnumByName(String name) {
		if(name == null) {
			return null;
		}
		for(StateEnum state : StateEnum.values()) {
			if(state.getName().equals(name)) {
				return state;
			}
		}
		return null;
	}
	
	public static StateEnum getEnumByCountryId(Integer countryId) {
		if(countryId == null) {
			return null;
		}
		for(StateEnum state : StateEnum.values()) {
			if(state.getCountryId() == countryId) {
				return state;
			}
		}
		return null;
	}
	
}
