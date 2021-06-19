package com.car.management.carmanagementapp.selenium.model;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class VehiclePageModel {

	private WebDriver driver;
	private IndexPageModel indexPageModel;

	@FindBy(id = "producer")
	WebElement inputProducer;

	@FindBy(id = "model")
	WebElement inputModel;

	@FindBy(id = "mileage")
	WebElement inputMileage;

	@FindBy(id = "licensePlate")
	WebElement inputLicensePlateNumber;

	@FindBy(id = "addCar")
	WebElement addCarBtn;

	@FindBy(xpath = "//*[@id=\"1\"]")
	WebElement deleteCarBtn;

	public VehiclePageModel(final WebDriver driver) {
		this.driver = driver;
		indexPageModel = new IndexPageModel(driver);
		PageFactory.initElements(driver, this);
	}

	public void setInputVehicleValue(String producer, String model, Integer mileage, String licensePlate) {

		inputProducer.clear();
		inputModel.clear();
		inputMileage.clear();
		inputLicensePlateNumber.clear();

		inputProducer.sendKeys(producer);
		inputModel.sendKeys(model);
		inputMileage.sendKeys(mileage.toString());
		inputLicensePlateNumber.sendKeys(licensePlate);
	}

	public void clickAddBtn() {
		addCarBtn.click();
	}

	public void clickDeleteBtn() {
		deleteCarBtn.click();
	}

	public boolean isCarDisplayed() {
		if (driver.findElement(By.id("vehiclesList")).isDisplayed())
			return true;

		return false;
	}

	public void openIndexPageAndRegisterUser() throws InterruptedException {
		
		WebDriverWait wait = new WebDriverWait(driver, 5000);

		driver.get("http://localhost:8080/");
		indexPageModel.clickButtons("Регистрация");

		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("registerModal"))));

		indexPageModel.setInputRegisterValue("testUser1", "testUser1@gmail.com", "qwe", "qwe");
		indexPageModel.clickButtons("Регистрирай");

		driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
	}

	public void openIndexPageAndLoginAndOpenNextPage(String pageUrl) throws InterruptedException {

		driver.get("http://localhost:8080/");

		indexPageModel.setInputLoginValue("testUser1", "qwe");
		indexPageModel.clickButtons("Влез");

		driver.get(pageUrl); 

		driver.manage().timeouts().pageLoadTimeout(1, TimeUnit.SECONDS);
	}
}
