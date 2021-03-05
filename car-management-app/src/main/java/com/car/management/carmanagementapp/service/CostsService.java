/**
 * 
 */
package com.car.management.carmanagementapp.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.management.carmanagementapp.bean.CostsBean;
import com.car.management.carmanagementapp.bean.VehicleBean;
import com.car.management.carmanagementapp.repository.ICostsRepository;

/**
 * @author Dimitar
 *
 */

@Service
public class CostsService {

	@Autowired
	private ICostsRepository costsRepository;

	@Autowired
	private VehicleService vehicleService;

	public void addCostToDatabasa(CostsBean cost, Integer vehicleId, String date) {

		VehicleBean vehicle = vehicleService.findCarById(vehicleId);

		if (vehicle == null)
			return;

		cost.setVehicle(vehicle);

		cost.setDate(LocalDate.parse(date));
		if (checkTypeOfCost(cost.getValidity())) {
			cost.setExpiredDate(convertDateString(LocalDate.parse(date), cost.getValidity()));
		}

		costsRepository.saveAndFlush(cost);
	}

	public List<CostsBean> getAllCosts(Integer vehicleId) {

		List<CostsBean> costs = new ArrayList<CostsBean>();

		for (CostsBean cost : costsRepository.findAll()) {

			if (vehicleId == cost.getVehicle().getId()) {
				costs.add(cost);
			}
		}

		return costs;
	}

	private LocalDate convertDateString(LocalDate date, int validity) {

		return date.plusMonths(validity);
	}

	private boolean checkTypeOfCost(Integer validity) {
		if (validity == 0)
			return false;

		return true;
	}
}
