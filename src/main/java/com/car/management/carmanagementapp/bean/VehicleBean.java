package com.car.management.carmanagementapp.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

<<<<<<< HEAD
import com.fasterxml.jackson.annotation.JsonIgnore;

=======
>>>>>>> a7911477d628f7f8d71d9ea3b5e07baeb1f8fcc9
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "vehicle")
@Getter
@Setter
public class VehicleBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "producer", nullable = false, length = 40)
	private String producer;

	@Column(name = "model", nullable = false, length = 40)
	private String model;

	@Column(name = "mileage", nullable = false, length = 40)
	private Integer startingMileage;

	@Column(name = "licensePlate", nullable = false, unique = true, length = 10)
	private String licensePlate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private UserBean owner;
<<<<<<< HEAD
	
	@JsonIgnore
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "vehicle") 	
	private List<CostsBean> costs;
	
	@JsonIgnore
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "vehicle")	
=======

	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "vehicle")
	private List<CostsBean> costs;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(mappedBy = "vehicle")
>>>>>>> a7911477d628f7f8d71d9ea3b5e07baeb1f8fcc9
	private List<ConsumptionBean> consumption;

}
