package com.omdev.um.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.omdev.um.entity.CountryEntity;

public interface CountryRepository extends JpaRepository<CountryEntity, Integer> {

}
