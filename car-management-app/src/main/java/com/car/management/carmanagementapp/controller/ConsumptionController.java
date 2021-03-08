/**
 * 
 */
package com.car.management.carmanagementapp.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.car.management.carmanagementapp.bean.ConsumptionBean;
import com.car.management.carmanagementapp.bean.UserBean;
import com.car.management.carmanagementapp.service.ConsumptionService;

/**
 * @author Dimitar
 *
 */

@RestController
public class ConsumptionController {

	@Autowired
	private ConsumptionService consumptionService;

	/**
	 * 
	 * @param quantity
	 * @param pricePerLiter
	 * @param actualMileage
	 * @param vehicleId
	 * @param session
	 * @return
	 */
	@PostMapping(path = "/consumption/add")
	public ResponseEntity<Boolean> creatConsumption(@RequestParam(value = "quantity") Double quantity,
			@RequestParam(value = "pricePerLiter") Double pricePerLiter,
			@RequestParam(value = "actualMileage") Integer actualMileage,
			@RequestParam(value = "vehicleId") Integer vehicleId, HttpSession session) {

		if (quantity <= 0 || pricePerLiter <= 0 || actualMileage <= 0)
			return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);

		UserBean user = (UserBean) session.getAttribute("user");

		if (user == null) {
			return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
		}

		ConsumptionBean consumption = new ConsumptionBean();

		consumption.setQuantity(quantity);
		consumption.setPrice(pricePerLiter * quantity);
		consumption.setActualMileage(actualMileage);

		consumptionService.addConsumption(consumption, vehicleId);

		return new ResponseEntity<>(true, HttpStatus.OK);
	}

	/**
	 * 
	 * @param vehicleId
	 * @param session
	 * @return
	 */
	@GetMapping(path = "/consumptions")
	public ResponseEntity<List<ConsumptionBean>> getAllConsumptionsByVehicle(
			@RequestParam(value = "vehicleId") Integer vehicleId, HttpSession session) {

		UserBean user = (UserBean) session.getAttribute("user");

		if (user == null)
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

		return new ResponseEntity<>(consumptionService.getAllConsumptions(vehicleId), HttpStatus.OK);
	}
}
