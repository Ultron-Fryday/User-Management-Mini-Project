package com.omdev.um.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.omdev.um.entity.StateEntity;

public interface StateRepository extends JpaRepository<StateEntity, Integer> {
	
	
	@Query(value = "SELECT * FROM state_tbl  WHERE country_id = :countryId", nativeQuery = true)
	public List<StateEntity> findByCountryId(Integer countryId);
}
