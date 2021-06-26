/**
 * 
 */
package com.car.management.carmanagementapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.management.carmanagementapp.bean.ConsumptionBean;
import com.car.management.carmanagementapp.bean.CostsBean;
import com.car.management.carmanagementapp.bean.VehicleBean;
import com.car.management.carmanagementapp.repository.IConsumptionRepository;
import com.car.management.carmanagementapp.repository.ICostsRepository;
import com.car.management.carmanagementapp.repository.IVehicleRepository;

/**
 * @author Dimitar
 *
 */

@Service
public class VehicleService {

	@Autowired
	private IVehicleRepository vehicleRepository;

	@Autowired
	private IConsumptionRepository consumptionRepository;

	@Autowired
	private ICostsRepository costsRepository;

	/**
	 * 
	 * @param vehicleId
	 * @param actualMileage
	 */
	public void editVehicleDistance(Integer vehicleId, Integer actualDistance) {
		vehicleRepository.getOne(vehicleId).setStartingMileage(actualDistance);
	}

	/**
	 * 
	 * @param vehicleId
	 * @return
	 */
	public VehicleBean findCarById(Integer vehicleId) {
		return vehicleRepository.getOne(vehicleId);
	}

	public void deleteVehicleById(Integer id) {
		VehicleBean vehicle = findCarById(id);

		for (ConsumptionBean consmuption : vehicle.getConsumption()) {
			consumptionRepository.deleteById(consmuption.getId());
		}

		for (CostsBean costs : vehicle.getCosts()) {
			costsRepository.deleteById(costs.getId());
		}

		vehicleRepository.delete(vehicle);
	}
	
	public boolean isLicensePlateExists(String licensePlate) {
		List<VehicleBean> vehicles = vehicleRepository.findAll();
		
		if(vehicles.contains(vehicleRepository.findByLicensePlate(licensePlate.toUpperCase())))
			return true;
		
		return false;
	}
	
//	public void editVehicleById(Integer vehicleId) {
//		
//	}
}
