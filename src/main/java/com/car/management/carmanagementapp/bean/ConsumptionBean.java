package com.car.management.carmanagementapp.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "consumption")
@Getter
@Setter
public class ConsumptionBean {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "quantity", nullable = false, precision = 2)
	private Double quantity;

	@Column(name = "pricePerLiter", nullable = false, precision = 1)
	private Double price;

	@Column(name = "actualDistance", nullable = false)
	private Integer actualDistance;
	
	@Column(name = "distance")
	private Integer distance;
	
	@Column(name = "avgConsumption")
	private Double avgConsumption;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "vehicle_id")
	private VehicleBean vehicle;

}
