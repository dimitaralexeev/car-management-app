/**
 * 
 */
package com.car.management.carmanagementapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.management.carmanagementapp.bean.VehicleBean;
import com.car.management.carmanagementapp.repository.IVehicleRepository;

/**
 * @author Dimitar
 *
 */

@Service
public class VehicleService {

	@Autowired
	private IVehicleRepository vehicleRepository;
	
	/**
	 * 
	 * @param vehicleId
	 * @param actualMileage
	 */
	public void editVehicleMileage(Integer vehicleId, Integer actualMileage) {
		vehicleRepository.getOne(vehicleId).setStartingMileage(actualMileage);
	}
	
	/**
	 * 
	 * @param vehicleId
	 * @return
	 */
	public VehicleBean findCarById(Integer vehicleId) {
		return vehicleRepository.getOne(vehicleId);
	}

}
