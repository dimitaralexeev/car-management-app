/**
 * 
 */
package com.car.management.carmanagementapp.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.assertj.core.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import com.car.management.carmanagementapp.bean.VehicleBean;
import com.car.management.carmanagementapp.repository.IVehicleRepository;

/**
 * Test class for {@link VehicleController}
 *
 */

@RunWith(Parameterized.class)
public class VehicleControllerTest {
	
	@Parameters(name = "{index}: with username={0}, pass1={1}, pass2={2} and expected message={3}")
	public static List<Object> data() {
		return Arrays.asList(new Object[][] { //
				{ }, //0
				 { null, "pass", "pass", "Въведете валидни входни аргументи" }, //1
				 { "username", null, "pass",  "Въведете валидни входни аргументи"}, //2
				 {"username", "pass", null,  "Въведете валидни входни аргументи"},//3
				 {"username", "pass", "pass",  "OK"},//4
				 {"username1", "pass", "pass",  "Потребител тези данни не съществува!"},//5
				 {"username", "pass1", "pass1",  "Потребител тези данни не съществува!"},//6
				 {"username", "pass", "pass1",  "Въведете еднакви пароли!"},//7
				 {"username", "pass1", "pass",  "Въведете еднакви пароли!"}//8
		});
	}
	
	@Parameter(0)
	public String producer;
	@Parameter(1)
	public String model;
	@Parameter(2)
	public Integer mileage;
	@Parameter(3)
	public String licensePlate;
	@Parameter(4)
	public HttpSession session;
	@Parameter(5)
	public String expectedMessage;
	
	private VehicleController vc;
	private IVehicleRepository ivr;
	
	@Before
	public void setup() {
		ivr = mock(IVehicleRepository.class);
		VehicleBean vehicle = new VehicleBean();
		vehicle.setProducer("Opel");
		vehicle.setModel("Manta");
		vehicle.setStartingMileage(180000);
		vehicle.setLicensePlate("PB2466AX");
		List<VehicleBean> vehicles = new ArrayList<VehicleBean>();
		doReturn(vehicles).when(ivr).findAll();
		
		vc = new VehicleController(ivr);
	}
	
	//public void testAddVehicle
}
