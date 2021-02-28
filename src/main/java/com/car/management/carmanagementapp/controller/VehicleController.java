package com.car.management.carmanagementapp.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//import com.car.management.carmanagementapp.bean.UserBean;
import com.car.management.carmanagementapp.bean.VehicleBean;
import com.car.management.carmanagementapp.repository.IVehicleRepository;

@RestController
public class VehicleController {

	IVehicleRepository vehicleRepository;

	public VehicleController(IVehicleRepository vehicleRepository) {
		this.vehicleRepository = vehicleRepository;
	}
	
	@PostMapping(path = "/vehicle/add")
	public String addVehicle(@RequestParam(value = "producer")String producer, 
							 @RequestParam(value = "model")String model, 
							 @RequestParam(value = "mileage")Integer mileage, 							 
							 @RequestParam(value = "licensePlate")String licensePlate,
							 HttpSession session) {
		
//		UserBean user = (UserBean) session.getAttribute("vehicle");
		
//		if(user != null) {
			
			VehicleBean vehicleBean = new VehicleBean();
			
			vehicleBean.setProducer(producer);
			vehicleBean.setModel(model);
			vehicleBean.setStartingMileage(mileage);
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
	
	@GetMapping(path = "/vehicle/{id}")
	public VehicleBean getVehicle(@PathVariable Integer id) {
		return vehicleRepository.findAll().stream().filter(vehicle -> vehicle.getId().equals(id)).findFirst().get();
	}
	
	@DeleteMapping(path = "/vehicle/delete")
	public void deleteVehicle(@RequestParam(value = "id") Integer id) {
		vehicleRepository.deleteById(id);
	}
}
