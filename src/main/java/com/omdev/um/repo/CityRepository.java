package com.omdev.um.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.omdev.um.entity.CityEntity;

public interface CityRepository extends JpaRepository<CityEntity, Integer> {

	@Query(value = "SELECT * FROM city_tbl WHERE state_id = :stateId", nativeQuery = true)
	public List<CityEntity> findByStateId(Integer stateId);
}
