package com.car.management.carmanagementapp.selenium.model;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProfilPageModel {
	
	private WebDriver driver;
	
	@FindBy(id = "fName")
	WebElement inputFirstName;
	
	@FindBy(id = "lName")
	WebElement inputLastName;
	
	@FindBy(id = "city")
	WebElement inputCityName;
	
	@FindBy(id = "editUser")
	WebElement addNamesBtn;
	
	public ProfilPageModel(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	public void setInputUserValue(String fName, String lName, String cityName) {
		inputFirstName.clear();
		inputLastName.clear();
		inputCityName.clear();
		
		inputFirstName.sendKeys(fName);
		inputLastName.sendKeys(lName);
		inputCityName.sendKeys(cityName);
	}
	
	public void clickAddUserValuesBtn() {
		addNamesBtn.click();
	}

	public String getUserFirstName() {
		
		return driver.findElement(By.id("postFirstName")).getText();
	}
}
