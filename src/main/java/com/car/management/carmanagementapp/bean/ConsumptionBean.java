package com.car.management.carmanagementapp.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "consumption")
public class ConsumptionBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "quantity", nullable = false)
	private double quantity;
	
	@Column(name = "pricePerLiter", nullable = false)
	private double pricePerLiter;
	
	@ManyToOne()
	@JoinColumn(name = "vehicle_id")
	private VehicleBean vehicle;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	public double getPricePerLiter() {
		return pricePerLiter;
	}

	public void setPricePerLiter(double pricePerLiter) {
		this.pricePerLiter = pricePerLiter;
	}

	public VehicleBean getVehicle() {
		return vehicle;
	}

	public void setVehicle(VehicleBean vehicle) {
		this.vehicle = vehicle;
	}
	
	
}
