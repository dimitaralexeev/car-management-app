package com.car.management.carmanagementapp;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.car.management.carmanagementapp.selenium.model.VehiclePageModel;

public class SeleniumTestWithVehiclePage {
	
	private WebDriver driver;  
	VehiclePageModel vehiclePageModel;
	
	@BeforeClass
	public static void setupClass() {
		System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
	}
	
	@Before
	public void setup() {
		driver = new ChromeDriver();
		vehiclePageModel = new VehiclePageModel(driver);
	}
	
	@Test
	public void cTestAddCar() throws InterruptedException {
		vehiclePageModel.openIndexPageAndRegisterUser();
		
		vehiclePageModel.openIndexPageAndLoginAndOpenNextPage("http://localhost:8080/vehicle.html");
		
		vehiclePageModel.setInputVehicleValue("Peugeot", "307CC", 214000, "PB1694KT");
		
		vehiclePageModel.clickAddBtn();
		
		driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
		
		final boolean result = vehiclePageModel.isCarDisplayed();
		
		assertEquals(true, result); 
	}
	 
	@Test
	public void dTestDeleteCar() throws InterruptedException {
		vehiclePageModel.openIndexPageAndLoginAndOpenNextPage("http://localhost:8080/vehicle.html");
		
		vehiclePageModel.clickDeleteBtn();
		
		WebDriverWait wait = new WebDriverWait(driver, 1000);
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());

		final String result = alert.getText();
		alert.accept();
		assertEquals("Car is deleted!", result);
	}
	
	@After
	public void after() {

		driver.close();
	}
}
