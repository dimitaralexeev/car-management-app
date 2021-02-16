package com.car.management.carmanagementapp.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CostsBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "typeOfCost", nullable = false)
	private String typeOfCost;
	
	@Column(name = "description", nullable = false, length = 1000)
	private String descprition;
	
	@Column(name = "validity", nullable = false)
	private int validity;
	
	@Column(name = "price", nullable = false)
	private double price;
	
	@Column(name = "date", nullable = false)
	private String date;
	
	@ManyToOne
	@JoinColumn(name = "vehicle_id")
	private VehicleBean vehicle;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTypeOfCost() {
		return typeOfCost;
	}

	public void setTypeOfCost(String typeOfCost) {
		this.typeOfCost = typeOfCost;
	}

	public String getDescprition() {
		return descprition;
	}

	public void setDescprition(String descprition) {
		this.descprition = descprition;
	}

	public int getValidity() {
		return validity;
	}

	public void setValidity(int validity) {
		this.validity = validity;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public VehicleBean getVehicle() {
		return vehicle;
	}

	public void setVehicle(VehicleBean vehicle) {
		this.vehicle = vehicle;
	}

}
