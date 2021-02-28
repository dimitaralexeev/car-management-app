package com.car.management.carmanagementapp.bean;

import java.util.Date;

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
@Table(name = "costs")
@Getter
@Setter
public class CostsBean{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "typeOfCost", nullable = false)
	private String typeOfCost;
	
	@Column(name = "description", nullable = false, length = 1000)
	private String descprition;
	
	@Column(name = "validity", nullable = false)
	private Integer validity;
	
	@Column(name = "price", nullable = false, precision = 2)
	private Double price;
	
	@Column(name = "date", nullable = false)
	private Date date;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "vehicle_id")
	private VehicleBean vehicle;

}
