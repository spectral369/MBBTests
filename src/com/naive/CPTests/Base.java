package com.naive.CPTests;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

public abstract class Base {
	
	protected static WebDriver driver;
	protected static WebDriverWait wait ;
	protected static JavascriptExecutor js ;
	
	/**
	 * <b>Description:</b><br>
	 *  Setup driver and options
	 */
	@BeforeSuite(alwaysRun=true)
	public void setup() {
		System.setProperty("webdriver.driver.chrome", "chromedriver");
		
		ChromeOptions op =  new ChromeOptions();
		op.addArguments("disable-infobars");
		op.addArguments("start-maximized");
		driver =  new ChromeDriver(op);
		wait =  new WebDriverWait(driver, 20);
		js = ((JavascriptExecutor)driver);
		System.out.println("init");
	}
	
	/**
	 * <b>Description:</b><br>
	 *  Re-init after each test class
	 */
	@BeforeTest(alwaysRun=true)
	public void reset() {
		driver.navigate().to("http://localhost:8080/test");
		wait.until(new ExpectedCondition<Boolean>() {
		public Boolean apply(WebDriver driver) {
			return js.executeScript("return document.readyState").equals("complete");
		}
		});
		System.out.println("reset");
	}
	/**
	 * <b>Description:</b><br>
	 *  Clean up method
	 */
	@AfterSuite(alwaysRun=true)
	public void cleanup() {
		driver.quit();
		wait =  null;
		js =  null;
		driver = null;
		System.gc();
		System.out.println("close");
	}

}
