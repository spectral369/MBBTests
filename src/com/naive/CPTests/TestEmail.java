package com.naive.CPTests;


import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.AssertJUnit;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class TestEmail extends Base {

	private Actions action;
	private WebElement elem;
	private String origEmail = "";

	/**
	 * <b>Description:</b><br>
	 * Log in And Go to CP
	 */
	@BeforeTest
	public void toCP() {
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
	 * Email Can be changed only if user completed the questionnaire
	 * or email is preset in the DB
	 */
	@Test(priority = 1)
	public void CheckIfEmailCanBeChanged() {
		
		 elem = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[1]/div/div[2]/div/div[2]/div/div/div[2]/div/div[2]/div/div[7]/div/div/div/div")));
		action.moveToElement(elem).pause(300).build().perform();// ^ it should be primary so you could use submit xpath

		AssertJUnit.assertFalse(elem.getAttribute("class").contains("v-disabled "));
		
	}

	/**
	 * <b>Description:</b><br>
	 * Go to Change Email Window and get previous EMail and<br>
	 * check for invalid email!
	 */
	@Test(priority = 2, dependsOnMethods={"CheckIfEmailCanBeChanged"})
	public void getToChangeMailGetPreviousMailAndCheckForInvalidEmail() {

		 elem = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Email')]")));
		action.moveToElement(elem).click().pause(4500).build().perform();

		elem = driver.findElement(By.xpath("//input[@class='v-textfield v-disabled v-widget']"));
		origEmail = elem.getAttribute("value");
		System.out.println(origEmail);
		elem = driver.findElement(By.xpath("//input[@placeholder='New E-Mail']"));
		action.moveToElement(elem).sendKeys(elem, "testasdasd@").pause(3000).build().perform();

		action.moveToElement(driver.findElement(By.xpath("//input[@class='v-textfield v-disabled v-widget']"))).build()
				.perform();
		action.moveToElement(elem).pause(200).build().perform();

		elem = wait.until(ExpectedConditions
				.presenceOfElementLocated(By.xpath("//span[@class='v-errorindicator v-errorindicator-error']")));
		AssertJUnit.assertTrue(elem.isDisplayed());
		elem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='gwt-HTML']")));

		AssertJUnit.assertTrue(elem.getAttribute("innerHTML").equals("E-Mail is not valid"));
	}

	/**
	 * <b>Description:</b><br>
	 * Check for invalid email re-type
	 */
	@Test(priority = 3, dependsOnMethods={"CheckIfEmailCanBeChanged"})
	public void checkForInvalidReEmail() {
		elem = driver.findElement(By.xpath("//input[@placeholder='Re-type your New E-mail']"));
		action.sendKeys(elem, "testasadas@").pause(500).build().perform();
		action.moveToElement(driver.findElement(By.xpath("//input[@placeholder='New E-Mail']"))).click().pause(600)
				.build().perform();
		elem = driver.findElement(By.xpath("//input[@placeholder='Re-type your New E-mail']"));
		action.moveToElement(elem).pause(400).build().perform();
		elem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='gwt-HTML']")));

		AssertJUnit.assertTrue(elem.getAttribute("innerHTML").equals("E-Mail is not valid"));
	}

	/**
	 * <b>Description:</b><br>
	 * Check if email re-type is not the same error
	 */
	@Test(priority = 4, dependsOnMethods={"CheckIfEmailCanBeChanged"})
	public void checkIfMailsAreNotTheSame() {
		elem = driver.findElement(By.xpath("//input[@placeholder='New E-Mail']"));
		elem.clear();
		action.sendKeys(elem, "testt@test.org");
		elem = driver.findElement(By.xpath("//input[@placeholder='Re-type your New E-mail']"));
		elem.clear();
		action.moveToElement(elem).sendKeys(elem, "testt@testr.org").pause(200).build().perform();
		action.moveToElement(driver.findElement(By.xpath("//input[@placeholder='New E-Mail']"))).click().pause(600)
				.build().perform();
		elem = driver.findElement(By.xpath("//input[@placeholder='Re-type your New E-mail']"));
		action.moveToElement(elem).pause(400).build().perform();
		elem = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='gwt-HTML']")));
		AssertJUnit.assertTrue(elem.getAttribute("innerHTML").equals("Emails don't match !"));
	}

	/**
	 * <b>Description:</b><br>
	 * Submit button should not be enabled if field are not validated
	 */
	@Test(priority = 5, dependsOnMethods={"CheckIfEmailCanBeChanged"})
	public void checkSubmitIsInvalidIfNotAllFieldAreSetup() {
		elem = driver.findElement(By.xpath("/html/body/div[2]/div[3]/div/div/div[3]/div/div/div[9]/div/div[1]/div"));// not
		// good
		action.moveToElement(elem).pause(100).build().perform();// ^ it should be primary so you could use submit xpath

		AssertJUnit.assertTrue(elem.getAttribute("class").contains("v-disabled "));
	}

	/**
	 * <b>Description:</b><br>
	 * enter valid emails and check if submit is clickable
	 */
	@Test(priority = 6, dependsOnMethods={"CheckIfEmailCanBeChanged"})
	public void checkIfSubmitIsClickable() {

		elem = driver.findElement(By.xpath("//input[@placeholder='Re-type your New E-mail']"));
		elem.clear();
		action.moveToElement(elem).sendKeys(elem, "testt@test.org").pause(400).build().perform();
		elem = driver.findElement(By.xpath("/html/body/div[2]/div[3]/div/div/div[3]/div/div/div[9]/div/div[1]/div"));
		action.moveToElement(elem).build().perform();// no need for build

		AssertJUnit.assertFalse(elem.getAttribute("class").contains("v-disabled "));

	}

	/**
	 * <b>Description:</b><br>
	 * Submit valid email and check if email changed in CP
	 */
	@Test(priority = 7, dependsOnMethods={"CheckIfEmailCanBeChanged"})
	public void submitNewEmailAndCheckIfEmailChanged() {
		elem = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("/html/body/div[2]/div[3]/div/div/div[3]/div/div/div[9]/div/div[1]/div")));
		action.moveToElement(elem).click().pause(3000).build().perform();
		elem = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Email')]")));
		action.moveToElement(elem).click().pause(2500).build().perform();
		elem = driver.findElement(By.xpath("//input[@class='v-textfield v-disabled v-widget']"));
		String currEmail = elem.getAttribute("value");
	
		elem  =  driver.findElement(By.xpath("//*[contains(text(),'ancel')]"));
		action.moveToElement(elem).click(elem).pause(500).build().perform();
		revert();
		LogOut();
		elem = wait.until(ExpectedConditions.presenceOfElementLocated(
				By.xpath("//div[@class='v-button v-widget borderless-colored v-button-borderless-colored']")));
		Assert.assertNotEquals(origEmail,currEmail );
	}
	
	private void revert() {
		elem = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Email')]")));
		action.moveToElement(elem).click().pause(4500).build().perform();
		elem = driver.findElement(By.xpath("//input[@placeholder='New E-Mail']"));
		action.moveToElement(elem).sendKeys(elem, "testc@test.org").pause(3000).build().perform();
		elem = driver.findElement(By.xpath("//input[@placeholder='Re-type your New E-mail']"));
		action.sendKeys(elem, "testc@test.org").pause(500).build().perform();
		elem = wait.until(ExpectedConditions.elementToBeClickable(
				By.xpath("/html/body/div[2]/div[3]/div/div/div[3]/div/div/div[9]/div/div[1]/div")));
		action.moveToElement(elem).click().pause(3000).build().perform();
	}

	private void LogOut() {
		elem = driver.findElement(By.xpath("//span[contains(text(),'Log')]"));
		action.moveToElement(elem).click().pause(4000).build().perform();
	}

}
