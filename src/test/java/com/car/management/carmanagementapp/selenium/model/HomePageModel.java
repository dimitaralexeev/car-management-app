package com.car.management.carmanagementapp.selenium.model;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePageModel {
	
	private WebDriver driver;
	
	public HomePageModel(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(id = "chooseCar")
	WebElement changeCarBtn;
	
	@FindBy(id = "costsTableBtn")
	WebElement consumptionTableBtn;
	
	@FindBy(id = "costsTableBtn")
	WebElement costTableBtn;
	
	@FindBy(id = "consumptionModalBtn")
	WebElement consumptionModalBtn;
	
	@FindBy(id = "costsModalBtn")
	WebElement costModalBtn;
	
	@FindBy(id = "addQuantity")
	WebElement inputQuantity;
	
	@FindBy(id = "addPricePerLiter")
	WebElement inputPricePerLiter;
	
	@FindBy(id = "addLastMileage")
	WebElement inputLastMileage;
	
	@FindBy(id = "addConsumption")
	WebElement addConsumptionBtn;
	
	@FindBy(id = "chooseCostType")
	WebElement changeCostTypeBtn;
	
	@FindBy(id = "addPrice")
	WebElement inputPrice;
	
	@FindBy(id = "addDate")
	WebElement inputDate;
	
	@FindBy(id = "chooseValidity")
	WebElement changeValidityBtn;
	
	@FindBy(id = "addDescription")
	WebElement inputDescription;
	
	@FindBy(id = "addCost")
	WebElement addCostBtn;
}
