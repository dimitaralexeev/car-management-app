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

		consumption.setDistance(consumption.getActualMileage() - vehicle.getStartingMileage());

		consumption.setAvgConsumption((consumption.getQuantity() * 100) / consumption.getDistance());

		vehicleService.editVehicleMileage(vehicle.getId(), consumption.getActualMileage());

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

			if (vehicleId == consumption.getVehicle().getId()) {
				consumptions.add(consumption);
			}
		}

		return consumptions;
	}
}
