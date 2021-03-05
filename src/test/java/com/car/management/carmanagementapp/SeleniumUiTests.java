/**
 * 
 */
package com.car.management.carmanagementapp;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author Dimitar
 *
 */
public class SeleniumUiTests {

	private WebDriver driver;

	@BeforeClass
	public static void setupClass() {
		System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
	}

	@Before
	public void setup() {
		driver = new ChromeDriver();
	} 

	/**
	 * Test for registration modal opening, registration function and login function
	 * Expected page: home.html
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void testModalOpenAndRegistrationThenLogin() throws InterruptedException {
		driver.get("http://localhost:8080/");
		driver.findElement(By.id("register")).click();
		Thread.sleep(500);
		driver.findElement(By.id("registerUser")).sendKeys("username");
		driver.findElement(By.id("registerEmail")).sendKeys("email@email.com");
		driver.findElement(By.id("registerPass")).sendKeys("asd");
		driver.findElement(By.id("confirmRegisterPass")).sendKeys("asd");
		driver.findElement(By.id("registerForm")).submit();
		Thread.sleep(500);
		driver.findElement(By.id("username")).sendKeys("username");
		driver.findElement(By.id("password")).sendKeys("asd");
		driver.findElement(By.id("loginForm")).submit();
		Thread.sleep(500);

		final String expectedUrl = "http://localhost:8080/home.html";
		final String result = driver.getCurrentUrl();

		assertEquals(expectedUrl, result);
	}

	@Test
	public void testCarPageInHeaderIsSelected() throws InterruptedException {
		login();
		WebElement carPageBtn = driver.findElement(By.id("carPage"));
		final boolean result = carPageBtn.isSelected();

		assertEquals(false, result);
	} 

	private void login() throws InterruptedException {
		driver.get("http://localhost:8080/");
		driver.findElement(By.id("username")).sendKeys("username");
		driver.findElement(By.id("password")).sendKeys("asd");
		driver.findElement(By.id("loginForm")).submit();
		Thread.sleep(500);
	}

	@After
	public void after() {

		driver.close();
	}
}
