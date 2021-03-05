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

import com.car.management.carmanagementapp.bean.CostsBean;
import com.car.management.carmanagementapp.bean.UserBean;
import com.car.management.carmanagementapp.service.CostsService;

/**
 * @author Dimitar
 *
 */

@RestController
public class CostsController {

	@Autowired
	private CostsService costsService;

	@PostMapping(path = "/cost/add")
	public ResponseEntity<Boolean> addCost(@RequestParam(value = "typeOfCost") String typeOfCost,
			@RequestParam(value = "price") Double price, @RequestParam(value = "date") String date,
			@RequestParam(value = "validity") Integer validity, @RequestParam(value = "descprition") String descprition,
			@RequestParam(value = "vehicleId") Integer vehicleId, HttpSession session) {

		if (price <= 0 || date == null || descprition == null || descprition == "")
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

		costsService.addCostToDatabasa(cost, vehicleId, date);

		return new ResponseEntity<>(true, HttpStatus.OK);
	}

	@GetMapping(path = "/costs")
	public ResponseEntity<List<CostsBean>> getAllCostsByVehicle(@RequestParam(value = "vehicleId") Integer vehicleId,
			HttpSession session) {

		UserBean user = (UserBean) session.getAttribute("user");

		if (user == null)
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);

		return new ResponseEntity<>(costsService.getAllCosts(vehicleId), HttpStatus.OK);
	}
}
