package com.car.management.carmanagementapp;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.car.management.carmanagementapp.selenium.model.ProfilPageModel;
import com.car.management.carmanagementapp.selenium.model.VehiclePageModel;

public class SeleniumTestWithProfilPage {

	private WebDriver driver;
	ProfilPageModel profilPageModel;
	VehiclePageModel vehiclePageModel;

	@BeforeClass
	public static void setupClass() {
		System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
	}
	
	@Before
	public void setup() {
		driver = new ChromeDriver();
		profilPageModel = new ProfilPageModel(driver);
		vehiclePageModel = new VehiclePageModel(driver);
	}
	
	@Test
	public void dTestAddUserInfo() throws InterruptedException {
		vehiclePageModel.openIndexPageAndLoginAndOpenNextPage("http://localhost:8080/profil.html");
		
		profilPageModel.setInputUserValue("Dimitar", "Todorov", "Plovdiv");
		profilPageModel.clickAddUserValuesBtn();
		
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		
		final String result = profilPageModel.getUserFirstName();
				
		assertEquals("Dimitar", result);
	}

	@After
	public void after() {

		driver.close();
	}

}
