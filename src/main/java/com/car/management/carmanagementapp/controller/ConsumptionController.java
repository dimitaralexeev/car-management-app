/**
 * 
 */
package com.car.management.carmanagementapp.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	 * @param actualDistance
	 * @param vehicleId
	 * @param session
	 * @return
	 */
	@PostMapping(path = "/consumption/add")
	public String creatConsumption(@RequestParam(value = "quantity") Double quantity,
			@RequestParam(value = "pricePerLiter") Double pricePerLiter,
			@RequestParam(value = "actualMileage") Integer actualDistance,
			@RequestParam(value = "vehicleId") Integer vehicleId, HttpSession session) {

		if(quantity < 0 || pricePerLiter < 0 || actualDistance < 0)
			return "Error: negative value";
		
		if (actualDistance <= consumptionService.getLastDistance(vehicleId))
			return "Error: distance";

		UserBean user = (UserBean) session.getAttribute("user");

		if (user == null) {
			return "Error: User not found";
		}

		ConsumptionBean consumption = new ConsumptionBean();

		consumption.setQuantity(quantity);
		consumption.setPrice(pricePerLiter * quantity);
		consumption.setActualDistance(actualDistance);

		consumptionService.addConsumption(consumption, vehicleId);

		return "OK";
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

	@DeleteMapping(path = "/deleteConsumption")
	public ResponseEntity<Boolean> deleteConsumptionById(@RequestParam(value = "vehicleId") Integer vehicleId,
			@RequestParam(value = "consumptionId") Integer consumptionId, HttpSession session) {

		UserBean user = (UserBean) session.getAttribute("user");

		if (user == null)
			return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);

		consumptionService.deleteConsumption(vehicleId, consumptionId);

		return new ResponseEntity<>(true, HttpStatus.OK);
	}
}
