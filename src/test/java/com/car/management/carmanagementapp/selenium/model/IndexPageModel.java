package com.car.management.carmanagementapp.selenium.model;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Index page model object
 * 
 * @author Dimitar
 *
 */
public class IndexPageModel {

	private WebDriver driver;

	@FindBy(id = "registerUser")
	WebElement inputRegisterUsername;

	@FindBy(id = "registerEmail")
	WebElement inputRegisterEmail;

	@FindBy(id = "registerPass")
	WebElement inputRegisterPass;

	@FindBy(id = "confirmRegisterPass")
	WebElement inputRegisterPassConfirm;

	@FindBy(id = "username")
	WebElement inputLoginUsername;

	@FindBy(id = "password")
	WebElement inputLoginPass;

	@FindBy(id = "register")
	WebElement registerModalBtn;

	@FindBy(id = "registerForm")
	WebElement registerSubmitForm;

	@FindBy(id = "loginForm")
	WebElement loginSubmitForm;

	/**
	 * 
	 * @param driver
	 */
	public IndexPageModel(final WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	/**
	 * 
	 */
	public void navigateToIndexPage() {
		driver.get("http://localhost:8080/");
	}

	/**
	 * 
	 * @param inputUsername
	 * @param inputEmail
	 * @param inputPass
	 * @param inputPassConf
	 */
	public void setInputRegisterValue(String inputUsername, String inputEmail, String inputPass, String inputPassConf) {
		inputRegisterUsername.clear();
		inputRegisterEmail.clear();
		inputRegisterPass.clear();
		inputRegisterPassConfirm.clear();
		
		inputRegisterUsername.sendKeys(inputUsername);
		inputRegisterEmail.sendKeys(inputEmail);
		inputRegisterPass.sendKeys(inputPass);
		inputRegisterPassConfirm.sendKeys(inputPassConf);
		
		driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
	}

	/**
	 * 
	 * @param inputUsername
	 * @param inputPass
	 */
	public void setInputLoginValue(String inputUsername, String inputPass) {
		inputLoginUsername.clear();
		inputLoginPass.clear();
		inputLoginUsername.sendKeys(inputUsername);
		inputLoginPass.sendKeys(inputPass);
		
		driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
	}

	public void clickButtons(String elementText) {
		switch (elementText) {
		case "Регистрация":
			registerModalBtn.click();
			break;
		case"Регистрирай":
			registerSubmitForm.submit();
			break;
		case"Влез":
			loginSubmitForm.submit();
			break;
		}
		
		driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
	}

	public String getPageUrl() {
		
		return driver.getCurrentUrl();
	}

	public boolean isRegisterModalDisplayed() throws InterruptedException {
		
		return driver.findElement(By.id("registerModal")).isDisplayed();
	}
}
