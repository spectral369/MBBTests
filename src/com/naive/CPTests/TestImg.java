package com.naive.CPTests;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestImg extends Base {

	private Actions action;
	private WebElement elem;
	private String headerUserIcon = "";
	private String CPUserIcon = "";
	private String uploadIcon = "";

	/**
	 * <b>Description:</b><br>
	 * Log in And Go to CP
	 */
	@BeforeTest
	public void toCP1() {
		action = new Actions(driver);
		elem = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[@class='v-button v-widget borderless-colored v-button-borderless-colored']")));

		action.moveToElement(elem).click().build().perform();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='v-window-wrap']")));

		elem = driver.findElement(By.xpath("//input[@type='text']"));

		action.moveToElement(elem).sendKeys(elem, "1234567891239").build().perform();
		elem = driver.findElement(By.xpath("//input[@type='password']"));
		action.moveToElement(elem).sendKeys(elem, "testing").build().perform();
		elem = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//div[@class='v-button v-widget primary v-button-primary']")));

		action.moveToElement(elem).click().build().perform();
		elem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'CP')]")));

		action.pause(700).click(elem).pause(1000).build().perform();
	}

	/**
	 * <b>Description:</b><br>
	 * Get paths of the user Icon and check if headerIcon and CP icon are the same!
	 * Note: only the names of the image file are check because of caching
	 */
	@Test(priority = 1)
	public void getAndCheckImagePaths() {
		WebElement elem = driver.findElement(
				By.xpath("/html/body/div[1]/div/div[2]/div/div[1]/div/div/div/div[1]/div/div[3]/div/div[1]/img"));
		headerUserIcon = elem.getAttribute("src");
		elem = driver.findElement(By
				.xpath("/html/body/div[1]/div/div[2]/div/div[2]/div/div/div[2]/div/div[2]/div/div[3]/div/div/div/img"));
		CPUserIcon = elem.getAttribute("src");

		elem = driver.findElement(By.xpath("//span[contains(text(),'con')]"));
		action.moveToElement(elem).click().pause(700).build().perform();

		elem = driver.findElement(By.xpath("//*[@class='v-image v-widget v-has-width v-has-height']"));
		uploadIcon = elem.getAttribute("src");
		AssertJUnit.assertEquals(headerUserIcon.substring(headerUserIcon.lastIndexOf("/"), headerUserIcon.length()),
				CPUserIcon.substring(CPUserIcon.lastIndexOf("/"), CPUserIcon.length()));

	}

	/**
	 * <b>Description:</b><br>
	 * Set new Icon and check if its displayed in the upload window!
	 */
	@Test(priority = 2)
	public void setnewIconAndCheckIfDisplayed() {

		elem = driver.findElement(By.xpath("//input[@type='file']"));
		String jsEX = "arguments[0].style.height='auto'; arguments[0].style.visibility='visible';";

		js.executeScript(jsEX, elem);
		elem.sendKeys("/home/spectral369/workspace_WEB/MyBloodBank/src/main/resources/uh.png");

		// "/home/spectral369/workspace_WEB/MyBloodBank/src/main/resources/uh.png"
		action.pause(5000).build().perform();
		elem = driver.findElement(By.xpath("//*[@class='v-image v-widget v-has-width v-has-height']"));
		Assert.assertNotEquals(elem.getAttribute("src").substring(elem.getAttribute("src").lastIndexOf("/"),
				elem.getAttribute("src").length()), uploadIcon.length());

	}

	/**
	 * <b>Description:</b><br>
	 * Submit new Image and check if header Icon and CP Icon changed! <b>Note:</b>
	 * only the names of the image file are check because of caching
	 */
	@Test(priority = 3)
	public void submitNewImageAndCheckIfAllUserIconChanged() {

		elem = driver.findElement(By.xpath("/html/body/div[2]/div[3]/div/div/div[3]/div/div/div[5]/div/div[1]/div"));
		elem.click();
		action.pause(3500).build().perform();
		elem = driver.findElement(
				By.xpath("/html/body/div[1]/div/div[2]/div/div[1]/div/div/div/div[1]/div/div[3]/div/div[1]/img"));
		String top = elem.getAttribute("src");

		/*
		 * Assert.assertNotEquals(top.substring(top.lastIndexOf("/"), top.length()),
		 * headerUserIcon.substring(headerUserIcon.lastIndexOf("/"),
		 * headerUserIcon.length()));
		 */

		elem = driver.findElement(By
				.xpath("/html/body/div[1]/div/div[2]/div/div[2]/div/div/div[2]/div/div[2]/div/div[3]/div/div/div/img"));

		String CP = elem.getAttribute("src");

		/*
		 * Assert.assertNotEquals(CP.substring(CP.lastIndexOf("/"), CP.length()),
		 * CPUserIcon.substring(CPUserIcon.lastIndexOf("/"), CPUserIcon.length()));
		 */
		revert();

		LogOut();

		elem = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[@class='v-button v-widget borderless-colored v-button-borderless-colored']")));

		Assert.assertNotEquals(top.substring(top.lastIndexOf("//"), top.length()),
				headerUserIcon.substring(headerUserIcon.lastIndexOf("//"), headerUserIcon.length()));
		Assert.assertNotEquals(CP.substring(CP.lastIndexOf("//"), CP.length()),
				CPUserIcon.substring(CPUserIcon.lastIndexOf("//"), CPUserIcon.length()));

	}

	private void revert() {
		elem = driver.findElement(By.xpath("//span[contains(text(),'con')]"));
		action.moveToElement(elem).click().pause(700).build().perform();
		elem = driver.findElement(By.xpath("//input[@type='file']"));
		String jsEX = "arguments[0].style.height='auto'; arguments[0].style.visibility='visible';";

		js.executeScript(jsEX, elem);
		elem.sendKeys("/home/spectral369/workspace_WEB/MyBloodBank/src/main/resources/user2.png");

		action.pause(5000).build().perform();
		elem = driver.findElement(By.xpath("/html/body/div[2]/div[3]/div/div/div[3]/div/div/div[5]/div/div[1]/div"));
		elem.click();
		action.pause(3500).build().perform();
	}

	private void LogOut() {
		elem = driver.findElement(By.xpath("//span[contains(text(),'Log')]"));
		action.moveToElement(elem).click().pause(4000).build().perform();
	}

}
