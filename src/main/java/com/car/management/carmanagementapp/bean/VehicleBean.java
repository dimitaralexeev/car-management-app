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

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "vehicle")
@Getter
@Setter
public class VehicleBean{
	
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
	
	@OneToMany(mappedBy = "vehicle", fetch = FetchType.EAGER)
	private List<CostsBean> costs;
	
	@OneToMany(mappedBy = "vehicle", fetch = FetchType.EAGER)
	private List<ConsumptionBean> consumption;

}
