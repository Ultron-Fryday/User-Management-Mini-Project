package com.omdev.um.constants;

import lombok.Getter;

@Getter
public enum CityEnum {
	Hyderabad(1, "Hyderabad", 1), Adilabad(2, "Adilabad", 1), Mumbai(3, "Mumbai", 2), Pune(4, "Pune", 2),
	Tirupati(5, "Tirupati", 3), Amravathi(6, "Amravathi", 3), LosAngeles(7, "Los Angeles", 4),
	SanFrancisco(8, "San Francisco", 4), Miami(9, "Miami", 5), StPetersburg(10, "St. Petersburg", 5);

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
