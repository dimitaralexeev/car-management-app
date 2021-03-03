package com.car.management.carmanagementapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.car.management.carmanagementapp.bean.UserBean;
import com.car.management.carmanagementapp.bean.VehicleBean;
import com.car.management.carmanagementapp.repository.IVehicleRepository;

/**
 * 
 * @author Dimitar
 *
 */
@RestController
public class VehicleController {

	private IVehicleRepository vehicleRepository;

	public VehicleController(IVehicleRepository vehicleRepository) {
		this.vehicleRepository = vehicleRepository;
	}

	/**
	 * 
	 * @param producer
	 * @param model
	 * @param mileage
	 * @param licensePlate
	 * @param session
	 * @return Vehicle id as a string
	 */
	@PostMapping(path = "/vehicle/add")
	public String addVehicle(@RequestParam(value = "producer") String producer,
			@RequestParam(value = "model") String model, @RequestParam(value = "mileage") Integer mileage,
			@RequestParam(value = "licensePlate") String licensePlate, HttpSession session) {

		UserBean user = (UserBean) session.getAttribute("user");

		if (user != null) {

			VehicleBean vehicleBean = new VehicleBean();

			vehicleBean.setProducer(producer);
			vehicleBean.setModel(model);
			vehicleBean.setStartingMileage(mileage);
			vehicleBean.setLicensePlate(licensePlate);
			vehicleBean.setOwner(user);

			vehicleBean = vehicleRepository.saveAndFlush(vehicleBean);

			if (vehicleBean != null) {
				return String.valueOf(vehicleBean.getId());
			}

			return "Error: Insert unsuccessfull";
		} else {
			return "Error: No user found";
		}
	}

	/**
	 * @param session
	 * @return
	 */
	@GetMapping(path = "/vehicle/all")
	public List<VehicleBean> getAllVehicles(HttpSession session) {
		UserBean user = (UserBean) session.getAttribute("user");

		if (user == null)
			return null;

		List<VehicleBean> ownerVehicles = new ArrayList<VehicleBean>();

		for (VehicleBean vehicle : vehicleRepository.findAll()) {
			if (vehicle.getOwner().getId() == user.getId()) {
				ownerVehicles.add(vehicle);
			}
		}

		return ownerVehicles;
	}

	/**
	 * @param session
	 * @param id
	 * @return
	 */
	@GetMapping(path = "/vehicle/{id}")
	public VehicleBean getVehicle(@PathVariable Integer id, HttpSession session) {
		UserBean user = (UserBean) session.getAttribute("user");

		if (user == null)
			return null;

		VehicleBean ownerVehicle = vehicleRepository.findAll().stream().filter(vehicle -> vehicle.getId().equals(id))
				.findFirst().get();

		if (ownerVehicle.getOwner().getId() != user.getId())
			return null;

		return ownerVehicle;
	}

	/**
	 * 
	 * @param id
	 * @param session
	 * @return
	 */
	@DeleteMapping(path = "/delete/{id}")
	public ResponseEntity<Boolean> deleteVehicle(@PathVariable(value = "id") Integer id, HttpSession session) {

		UserBean user = (UserBean) session.getAttribute("user");

		if (user == null)
			return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);

		Optional<VehicleBean> optionalVehicle = vehicleRepository.findById(id);

		if (optionalVehicle.isPresent()) {
			VehicleBean vehicle = optionalVehicle.get();

			if (vehicle.getOwner().getId() == user.getId()) {
				vehicleRepository.delete(vehicle);
				return new ResponseEntity<>(true, HttpStatus.OK);
			} else {
				return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
			}
		} else {
			return new ResponseEntity<>(false, HttpStatus.NOT_FOUND);
		}
	}
}
