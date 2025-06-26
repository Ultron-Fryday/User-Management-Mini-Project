package com.omdev.um.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user_tbl")
@Setter
@Getter
public class UserEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(length = 100)
	private String name;
	@Column(length = 100,unique = true)
	private String email;
	@Column(length = 10)
	private String phone;
	@Column(length = 100)
	private String password;
	@Column(name = "password_reset")
	private boolean passwordReset;
	@ManyToOne
	@JoinColumn(name="country_id",nullable = false)
	private CountryEntity country;
	@ManyToOne
	@JoinColumn(name="state_id",nullable = false)
	private StateEntity state;
	@ManyToOne
	@JoinColumn(name="city_id",nullable = false)
	private CityEntity city;
	@CreationTimestamp
	@Column(name = "create_date")
	private LocalDate createdDate;
	@Column(name = "updated_date")
	@UpdateTimestamp
	private LocalDate updatedDate;

}
