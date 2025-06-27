package com.omdev.um.constants;

import lombok.Getter;

@Getter
public enum CityEnum {
	HYDERBAD(1, "Hyderabad", 1), ADILABAD(2, "Adilabad", 1), MUMBAI(3, "Mumbai", 2), PUNE(4, "Pune", 2),
	TIRUPATI(5, "Tirupati", 3), AMRAVATHI(6, "Amravathi", 3), LOSANGELES(7, "Los Angeles", 4),
	SANFRANCISCO(8, "San Francisco", 4), MIAMI(9, "Miami", 5), STPETERSBURG(10, "St. Petersburg", 5);

	CityEnum(int id, String name, int stateId) {
		this.id = id;
		this.name = name;
		this.stateId = stateId;
	}

	private Integer id;
	private String name;
	private Integer stateId;
	
	public static CityEnum getEnumById(Integer id) {
		if(id == null) {
			return null;
		}
		for(CityEnum city : CityEnum.values()) {
			if(city.getId() == id) {
				return city;
			}
		}
		return null;
	}
	
	public static CityEnum getEnumByName(String name) {
		if(name == null) {
			return null;
		}
		for(CityEnum city : CityEnum.values()) {
			if(city.getName().equals(name)) {
				return city;
			}
		}
		return null;
	}
	
	public static CityEnum getEnumByStateId(Integer stateId) {
		if(stateId == null) {
			return null;
		}
		for(CityEnum city : CityEnum.values()) {
			if(city.getStateId() == stateId) {
				return city;
			}
		}
		return null;
	}

}
