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

import com.car.management.carmanagementapp.bean.CostsBean;
import com.car.management.carmanagementapp.bean.UserBean;
import com.car.management.carmanagementapp.service.CostsService;
import com.car.management.carmanagementapp.service.EventService;

/**
 * @author Dimitar
 *
 */

@RestController
public class CostsController {

	@Autowired
	private CostsService costsService;

	@Autowired
	private EventService eventService;

	/**
	 * 
	 * @param typeOfCost
	 * @param price
	 * @param date
	 * @param validity
	 * @param descprition
	 * @param vehicleId
	 * @param session
	 * @return
	 */
	@PostMapping(path = "/cost/add")
	public ResponseEntity<Boolean> addCost(@RequestParam(value = "typeOfCost") String typeOfCost,
			@RequestParam(value = "price") Double price, @RequestParam(value = "date") String date,
			@RequestParam(value = "validity") Integer validity, @RequestParam(value = "descprition") String descprition,
			@RequestParam(value = "vehicleId") Integer vehicleId, HttpSession session) {

		if (price < 0 || date.equals(null) || descprition.equals(null) || date.equals("")
				|| descprition.equals("") || validity < 0 || vehicleId <= 0 || typeOfCost.equals(null))
			return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
		
		if(!typeOfCost.equals("repair") && validity.equals(0))
			return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
		
		UserBean user = (UserBean) session.getAttribute("user");

		if (user == null) {
			return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);
		}

		CostsBean cost = new CostsBean();

		cost.setTypeOfCost(typeOfCost);
		cost.setPrice(price);
		cost.setValidity(validity);
		cost.setDescprition(descprition);

		costsService.addCostToDatabase(cost, vehicleId, date);

		return new ResponseEntity<>(true, HttpStatus.OK);
	}

	/**
	 * 
	 * @param vehicleId
	 * @param session
	 * @return
	 */
	@GetMapping(path = "/costs")
	public ResponseEntity<List<CostsBean>> getAllCostsByVehicle(@RequestParam(value = "vehicleId") Integer vehicleId,
			HttpSession session) {

		if (vehicleId <= 0)
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

		UserBean user = (UserBean) session.getAttribute("user");

		if (user == null)
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

		return new ResponseEntity<>(costsService.getAllCosts(vehicleId), HttpStatus.OK);
	}

	/**
	 * 
	 * @param session
	 * @return
	 */
	@GetMapping(path = "/expiredCosts")
	public ResponseEntity<List<CostsBean>> getExpiredCosts(HttpSession session) {

		UserBean user = (UserBean) session.getAttribute("user");

		if (user == null)
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

		return new ResponseEntity<>(eventService.getExpiredCosts(eventService.getUserByUsername(user.getUsername())),
				HttpStatus.OK);
	}

	/**
	 * 
	 * @param vehicleId
	 * @param costId
	 * @param session
	 * @return
	 */
	@DeleteMapping(path = "/deleteCost")
	public ResponseEntity<Boolean> deleteCostById(@RequestParam(value = "vehicleId") Integer vehicleId,
			@RequestParam(value = "costId") Integer costId, HttpSession session) {

		if (vehicleId <= 0 || costId <= 0)
			return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);

		UserBean user = (UserBean) session.getAttribute("user");

		if (user == null)
			return new ResponseEntity<>(false, HttpStatus.UNAUTHORIZED);

		costsService.deleteCostById(vehicleId, costId);

		return new ResponseEntity<>(true, HttpStatus.OK);
	}
}
