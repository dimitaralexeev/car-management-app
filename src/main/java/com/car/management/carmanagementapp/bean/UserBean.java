package com.car.management.carmanagementapp.bean;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class UserBean {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "username", nullable = false, unique = true, length = 40)
	private String username;
	
	@Column(name = "password", nullable = false, unique = true, length = 32)
	private String password;
	
	@Column(name = "email", nullable = false, unique = true, length = 256)
	private String email;
	
	@Column(name = "name", length = 256)
	private String name;
	
	@OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
	private List<VehicleBean> vehicles;
	
	public UserBean() {}
	
	public UserBean(String username, String email) {
		this.username = username;
		this.email = email;
	}
	
	public UserBean(String username, String password, String email) {
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<VehicleBean> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<VehicleBean> vehicles) {
		this.vehicles = vehicles;
	}
	
	
}
