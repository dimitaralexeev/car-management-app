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

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "user")
@Getter 
@Setter
public class UserBean{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "username", nullable = false, unique = true, length = 40)
	private String username;
	
	@Column(name = "password", nullable = false, unique = true, length = 32)
	private String password;
	
	@Column(name = "email", nullable = false, unique = true, length = 256)
	private String email;
	
	@Column(name = "fName", length = 256, nullable = false)
	private String fName;
	
	@Column(name = "lName", length = 256, nullable = false)
	private String lName;
	
	@OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
	private List<VehicleBean> vehicles;
	
//	public UserBean() {}
//	
//	public UserBean(String username, String email) {
//		this.username = username;
//		this.email = email;
//	}
//	
//	public UserBean(String username, String password, String email) {
//		this.username = username;
//		this.password = password;
//		this.email = email;
//	}
}
