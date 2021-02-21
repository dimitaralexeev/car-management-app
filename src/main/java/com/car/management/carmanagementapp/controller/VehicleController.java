package com.car.management.carmanagementapp.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import com.car.management.carmanagementapp.bean.UserBean;
import com.car.management.carmanagementapp.bean.VehicleBean;
import com.car.management.carmanagementapp.repository.VehicleRepository;

@RestController
public class VehicleController {

	private VehicleRepository vehicleRepository;

	public VehicleController(VehicleRepository vehicleRepository) {
		this.vehicleRepository = vehicleRepository;
	}
	
	@PostMapping(path = "/vehicle/add")
	public String addVehicle(@RequestParam(value = "producer")String producer, 
							 @RequestParam(value = "model")String model, 
							 @RequestParam(value = "mileage")int mileage, 
							 @RequestParam(value = "vinNumber")String vinNumber, 
							 @RequestParam(value = "licensePlate")String licensePlate,
							 HttpSession session) {
		
//		UserBean user = (UserBean) session.getAttribute("vehicle");
		
//		if(user != null) {
			
			VehicleBean vehicleBean = new VehicleBean();
			
			vehicleBean.setProducer(producer);
			vehicleBean.setModel(model);
			vehicleBean.setMileage(mileage);
			vehicleBean.setVinNumber(vinNumber);
			vehicleBean.setLicensePlate(licensePlate);
			//vehicleBean.setOwner(user);
			
			vehicleBean = vehicleRepository.saveAndFlush(vehicleBean);
			
			if(vehicleBean != null) {
				return String.valueOf(vehicleBean.getId());
			}
			
			return "Insert unsuccessfull";
//		}else {
//			return "No user found";
//		}
	}
	
	@GetMapping(path = "/vehicle/all")
	public List<VehicleBean> getAllVehicles(){
		return vehicleRepository.findAll();
	}
	
	
}
