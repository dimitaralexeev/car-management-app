package com.car.management.carmanagementapp.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.management.carmanagementapp.bean.CostsBean;
import com.car.management.carmanagementapp.bean.UserBean;
import com.car.management.carmanagementapp.bean.VehicleBean;
import com.car.management.carmanagementapp.repository.IUserRepository;

@Service
public class EventService {
	
	@Autowired
	private IUserRepository userRepository;
	
	@Autowired
	private SendEmailService sendEmailService;
	
	private LocalDate ld = LocalDate.now();
	
	public List<UserBean> getAllUsers(){
		
		List<UserBean> users = new ArrayList<>();
		
		for(UserBean user : userRepository.findAll()) {
			if(user != null) {
				users.add(user);
			}
		}
		
		return users;
	}
	
	public UserBean getUserByUsername(String username) {
		UserBean user = userRepository.findByUsername(username);
		
		return user;
	}
	
	private List<VehicleBean> getUserVehicles(List<UserBean> users) {
		List<VehicleBean> vehicles = new ArrayList<VehicleBean>();
		
		for(UserBean user : users) {
			for(VehicleBean vehicle : user.getVehicles()) {
				vehicles.add(vehicle);
			}
		}
		
		return vehicles;
	}
	
	public void checkForVehicleEventAndSendEmailToUser(List<UserBean> users) {
		
		List<VehicleBean> vehicles = getUserVehicles(users);
		
		List<CostsBean> costs = new ArrayList<CostsBean>();
		
		for(VehicleBean vehicle : vehicles) {
			for(CostsBean cost : vehicle.getCosts()) {
				if(cost.getTypeOfCost().equals("insurance") || cost.getTypeOfCost().equals("yearCheck")) {
					costs.add(cost);
				}
			}
		}
		
		for(CostsBean cost : costs) {
			if(cost.getExpiredDate().minusDays(2).equals(ld)) {
				
				sendEmailService.sendEmail(cost.getVehicle().getOwner().getEmail(), "Your " + cost.getTypeOfCost() + " expires in " 
				+ cost.getExpiredDate(), "You have a charge for your car!");
			}
		}
	}

	public List<CostsBean> getExpiredCosts(UserBean user) {
		
		List<CostsBean> expiredCosts = new ArrayList<CostsBean>();
		
		for(VehicleBean vehicle : user.getVehicles()) {
			for(CostsBean cost : vehicle.getCosts()) {
				if(cost.getTypeOfCost().equals("insurance") || cost.getTypeOfCost().equals("yearCheck")) {
					if(ld.isBefore(cost.getExpiredDate()) && ld.isAfter(cost.getExpiredDate().minusDays(7))) {
						expiredCosts.add(cost);
					}
				}
			}
		}
		
		return expiredCosts;
	}
}
