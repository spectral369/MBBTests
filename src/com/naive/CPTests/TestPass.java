package com.naive.CPTests;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestPass extends Base {

	private Actions action;
	private WebElement elem;
	private List<WebElement> list;

	/**
	 * <b>Description:</b><br>
	 * Log in And Go to CP
	 */
	@BeforeTest
	public void toCP2() {
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
	 * Get to ChangePassword Window and check for<br>
	 * new password length that must be at least 6 chars long
	 */
	@Test(priority = 1)
	public void getToChangePassWindowAndCheckForLengthErr() {
		elem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Pass')]")));
		action.moveToElement(elem).click().pause(3500).build().perform();

		list = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@type='password']")));
		action.moveToElement(list.get(0)).sendKeys(list.get(0), "asd").pause(700).build().perform();
		action.moveToElement(list.get(1)).pause(500).build().perform();

		action.moveToElement(list.get(0)).pause(700).build().perform();
		elem = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//span[@class='v-errorindicator v-errorindicator-error']")));
		AssertJUnit.assertTrue(elem.isDisplayed());
		elem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='gwt-HTML']")));
		AssertJUnit.assertEquals(elem.getAttribute("innerHTML"), "Password Must be at least 6 characters !");
	}

	/**
	 * <b>Description:</b><br>
	 * Verify new password
	 */
	@Test(priority = 2)
	public void checkIfPasswordNotSame() {
		elem = list.get(0);
		action.moveToElement(elem).click().build().perform();
		elem.clear();
		action.moveToElement(list.get(0)).sendKeys(list.get(0), "testing2").pause(300).build().perform();

		action.moveToElement(list.get(1)).sendKeys(list.get(1), "testing3").pause(700).build().perform();

		action.moveToElement(list.get(2)).pause(400).build().perform();
		action.moveToElement(list.get(1)).pause(600).build().perform();
		elem = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//span[@class='v-errorindicator v-errorindicator-error']")));
		AssertJUnit.assertTrue(elem.isDisplayed());
		elem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='gwt-HTML']")));
		AssertJUnit.assertTrue(elem.getAttribute("innerHTML").contains("Passwords don't match !"));

	}

	/**
	 * <b>Description:</b><br>
	 * Check if submit button is disabled if password field are not validated
	 */
	@Test(priority = 3)
	public void checkSubmitToBeDisabed() {
		AssertJUnit.assertTrue(!driver.findElements(By.xpath("//*[@class='v-button v-disabled v-widget']")).isEmpty());
	}

	/**
	 * <b>Description:</b><br>
	 * Update and check if you can login with the new password
	 */
	@Test(priority = 4)
	public void updatePasswordAndCheckIfYouCanLogInWithTheNewPassword() {

		elem = list.get(1);
		action.moveToElement(elem).click().build().perform();
		elem.clear();
		action.moveToElement(list.get(1)).sendKeys(list.get(1), "testing2").pause(500).build().perform();

		action.moveToElement(list.get(2)).sendKeys(list.get(2), "testing").pause(1500).build().perform();

		elem = driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/div/div/div[3]/div/div/div[9]/div/div[1]/div/span/span[2]"));
		action.moveToElement(elem).click().pause(3000).build().perform();
		elem = driver.findElement(By.xpath("//span[contains(text(),'Log')]"));
		action.moveToElement(elem).click().pause(4000).build().perform();
		LogIn("testing2");
		String greet = driver.findElement(By.xpath(
				"/html/body/div[1]/div/div[2]/div/div[1]/div/div/div/div[1]/div/div[3]/div/div[3]/div/span/span"))
				.getAttribute("innerHTML");
	
		revert();
		LogOut();
		elem = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[@class='v-button v-widget borderless-colored v-button-borderless-colored']")));
		AssertJUnit.assertTrue(greet.contains("Welcome"));
	}

	private void LogIn(String pass) {
		elem = wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//div[@class='v-button v-widget borderless-colored v-button-borderless-colored']")));

		action.moveToElement(elem).click().build().perform();

		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class='v-window-wrap']")));

		elem = driver.findElement(By.xpath("//input[@type='text']"));

		action.moveToElement(elem).sendKeys(elem, "1234567891239").build().perform();
		elem = driver.findElement(By.xpath("//input[@type='password']"));
		action.moveToElement(elem).sendKeys(elem, pass).build().perform();
		elem = wait.until(ExpectedConditions
				.elementToBeClickable(By.xpath("//div[@class='v-button v-widget primary v-button-primary']")));

		action.moveToElement(elem).click().build().perform();
		elem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'CP')]")));

		action.pause(700).click(elem).pause(1000).build().perform();

	}

	private void revert() {
		elem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[contains(text(),'CP')]")));
		action.pause(700).click(elem).pause(1000).build().perform();
		elem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Pass')]")));
		action.moveToElement(elem).click().pause(3500).build().perform();

		list = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath("//*[@type='password']")));
		action.moveToElement(list.get(0)).sendKeys(list.get(0), "testing").pause(700).build().perform();
		action.moveToElement(list.get(1)).sendKeys(list.get(1), "testing").pause(700).build().perform();
		action.moveToElement(list.get(2)).sendKeys(list.get(2), "testing2").pause(700).build().perform();
		elem = driver.findElement(
				By.xpath("/html/body/div[2]/div[3]/div/div/div[3]/div/div/div[9]/div/div[1]/div/span/span[2]"));
		action.moveToElement(elem).click().pause(3000).build().perform();
	}

	private void LogOut() {
		elem = driver.findElement(By.xpath("//span[contains(text(),'Log')]"));
		action.moveToElement(elem).click().pause(4000).build().perform();
	}
}

