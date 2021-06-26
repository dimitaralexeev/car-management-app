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

	/**
	 * 
	 * @param cost
	 * @param vehicleId
	 * @param date
	 */
	public void addCostToDatabase(CostsBean cost, Integer vehicleId, String date) {

		VehicleBean vehicle = vehicleService.findCarById(vehicleId);

		if (vehicle == null)
			return;

		cost.setVehicle(vehicle);

		cost.setDate(LocalDate.parse(date));
		if (checkTypeOfCostValidity(cost.getValidity())) {
			cost.setExpiredDate(calculateExpiredDate(LocalDate.parse(date), cost.getValidity()));
		}

		costsRepository.saveAndFlush(cost);
	}

	/**
	 * 
	 * @param vehicleId
	 * @return
	 */
	public List<CostsBean> getAllCosts(Integer vehicleId) {

		List<CostsBean> costs = new ArrayList<CostsBean>();

		for (CostsBean cost : costsRepository.findAll()) {

			if (vehicleId.equals(cost.getVehicle().getId())) {
				costs.add(cost);
			}
		}

		return costs;
	}

	/**
	 * 
	 * @param date
	 * @param validity
	 * @return
	 */
	private LocalDate calculateExpiredDate(LocalDate date, int validity) {

		return date.plusMonths(validity);
	}

	/**
	 * 
	 * @param validity
	 * @return
	 */
	private boolean checkTypeOfCostValidity(Integer validity) {
		if (validity == 0)
			return false;

		return true;
	}
	
	public void deleteCostById(Integer vehicleId, Integer costId) {
		
		CostsBean cost = costsRepository.getOne(costId);
		
		if(vehicleId.equals(cost.getVehicle().getId())) {
			costsRepository.deleteById(costId);
		}
	}
	
//	public void editCostById(Integer vehicleId, Integer costId) {
//		
//	}
}
