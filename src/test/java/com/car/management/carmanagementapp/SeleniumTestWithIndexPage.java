/**
 * 
 */
package com.car.management.carmanagementapp;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.car.management.carmanagementapp.selenium.model.IndexPageModel;

/**
 * @author Dimitar
 *
 */
public class SeleniumTestWithIndexPage {

	private WebDriver driver;
	IndexPageModel indexPageModel;

	@BeforeClass
	public static void setupClass() {
		System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
	}

	@Before
	public void setup() {
		driver = new ChromeDriver();
		indexPageModel = new IndexPageModel(driver);
	}

	@Test
	public void aTestRegisterUser() throws InterruptedException {
		indexPageModel.navigateToIndexPage();
		
		indexPageModel.clickButtons("Регистрация");

		indexPageModel.setInputRegisterValue("dimitar_alexeev", "debeliya@gmail.com", "asd", "asd");
		
		indexPageModel.clickButtons("Регистрирай");
		Thread.sleep(5000);
		final boolean result = indexPageModel.isRegisterModalDisplayed();//driver.findElement(By.id("registerModal")).isDisplayed();

		assertEquals(false, result);
	}
	
	@Test
	public void bTestLoginUser() throws InterruptedException {
		indexPageModel.navigateToIndexPage();
		indexPageModel.setInputLoginValue("dimitar_alexeev", "asd");
		indexPageModel.clickButtons("Влез");
		
		final String result = driver.getCurrentUrl();//indexPageModel.getPageUrl();
		
		assertEquals("http://localhost:8080/home.html", result);
	}

	@After
	public void after() {

		driver.close();
	}
}
