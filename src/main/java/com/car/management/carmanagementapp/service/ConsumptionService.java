/**
 * 
 */
package com.car.management.carmanagementapp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.management.carmanagementapp.bean.ConsumptionBean;
import com.car.management.carmanagementapp.bean.VehicleBean;
import com.car.management.carmanagementapp.repository.IConsumptionRepository;

/**
 * @author Dimitar
 *
 */

@Service
public class ConsumptionService {

	@Autowired
	private IConsumptionRepository consumptionRepository;

	@Autowired
	private VehicleService vehicleService;

	/**
	 * 
	 * @param consumption
	 * @param vehicleId
	 */
	public void addConsumption(ConsumptionBean consumption, Integer vehicleId) {

		VehicleBean vehicle = vehicleService.findCarById(vehicleId);

		if (vehicle == null)
			return;

		consumption.setVehicle(vehicle);

		consumption.setDistance(consumption.getActualDistance() - vehicle.getStartingMileage());

		consumption.setAvgConsumption((consumption.getQuantity() * 100) / consumption.getDistance());

		vehicleService.editVehicleDistance(vehicle.getId(), consumption.getActualDistance());

		consumptionRepository.saveAndFlush(consumption);
	}

	/**
	 * 
	 * @param vehicleId
	 * @return
	 */
	public List<ConsumptionBean> getAllConsumptions(Integer vehicleId) {

		List<ConsumptionBean> consumptions = new ArrayList<ConsumptionBean>();

		for (ConsumptionBean consumption : consumptionRepository.findAll()) {

			if (vehicleId.equals(consumption.getVehicle().getId())) {
				consumptions.add(consumption);
			}
		}

		return consumptions;
	}
	
	/**
	 * 
	 * @param vehicleId
	 * @param consumptionId
	 */
	public void deleteConsumption(Integer vehicleId, Integer consumptionId) {
		
		ConsumptionBean consumption = consumptionRepository.getOne(consumptionId);
		
		if(vehicleId.equals(consumption.getVehicle().getId())) {
			vehicleService.editVehicleDistance(vehicleId, vehicleService.findCarById(vehicleId).getStartingMileage() - consumption.getDistance());
			consumptionRepository.deleteById(consumptionId);
		}
	}
	
	/**
	 * 
	 * @param vehicleId
	 * @return
	 */
	public Integer getLastDistance(Integer vehicleId) {
		VehicleBean vehicle = vehicleService.findCarById(vehicleId);
		
		return vehicle.getStartingMileage();
	}
}
