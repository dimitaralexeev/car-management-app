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

@Entity
@Table(name = "vehicle")
public class VehicleBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "producer", nullable = false, length = 40)
	private String producer;
	
	@Column(name = "model", nullable = false, length = 40)
	private String model;
	
	@Column(name = "mileage", nullable = false, length = 40)
	private int mileage;
	
	@Column(name = "vinNumber", unique = true, length = 40)
	private String vinNumber;
	
	@Column(name = "licensePlate", nullable = false, unique = true, length = 10)
	private String licensePlate;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private UserBean owner;
	
	@OneToMany(mappedBy = "vehicle", fetch = FetchType.EAGER)
	private List<CostsBean> costs;
	
	@OneToMany(mappedBy = "vehicle", fetch = FetchType.EAGER)
	private List<ConsumptionBean> consumption;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getProducer() {
		return producer;
	}

	public void setProducer(String producer) {
		this.producer = producer;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getMileage() {
		return mileage;
	}

	public void setMileage(int mileage) {
		this.mileage = mileage;
	}

	public String getVinNumber() {
		return vinNumber;
	}

	public void setVinNumber(String vinNumber) {
		this.vinNumber = vinNumber;
	}

	public UserBean getOwner() {
		return owner;
	}

	public void setOwner(UserBean owner) {
		this.owner = owner;
	}

	public List<CostsBean> getCosts() {
		return costs;
	}

	public void setCosts(List<CostsBean> costs) {
		this.costs = costs;
	}

	public List<ConsumptionBean> getConsumption() {
		return consumption;
	}

	public void setConsumption(List<ConsumptionBean> consumption) {
		this.consumption = consumption;
	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}
	
	
}
