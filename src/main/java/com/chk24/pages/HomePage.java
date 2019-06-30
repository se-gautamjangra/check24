package com.chk24.pages;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.chk24.base.TestBase;

public class HomePage extends TestBase {

	@FindBy(xpath = "//a[contains(@class,'c24-meinkonto-reflink')]//font[text()='Sign in']")
	WebElement signIn;

	@FindBy(xpath = "//span[@class='c24-customer-hover-corner']")
	WebElement customerCornerHover;

	@FindBy(xpath = "//a//font[contains(text(),'Start here')]")
	WebElement newCustomerLink;

	@FindBy(xpath = "//a[@title='Sign out']")
	WebElement signout;

	@FindBy(xpath = "//h1[contains(@class,'headline')]")
	WebElement headline;

	@FindBy(xpath = "//a[@title='travel']")
	WebElement travel;

	// Initializing the Page Objects:
	public HomePage() {
		PageFactory.initElements(driver, this);
	}

	public String verifyHomePageTitle() {
		return driver.getTitle();
	}

	public RegisterPage clickOnNewCustomerLink() {
		Actions action = new Actions(driver);
		action.moveToElement(customerCornerHover).build().perform();
		newCustomerLink.click();
		return new RegisterPage();
	}

	public RegisterPage clickOnSignOut() {
		Actions action = new Actions(driver);
		action.moveToElement(customerCornerHover).build().perform();
		signout.click();
		return new RegisterPage();
	}

	public void clickSignIn() {
		signIn.click();
		waitForPageLoad();
	}

	public String getRegisterPageHeading() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return headline.getText();
	}

	public void escapeWelcomePopUp() {
		customerCornerHover.sendKeys(Keys.ESCAPE);
	}

	public TravelPage clickTravelTab() {
		travel.click();
		waitForPageLoad();
		return new TravelPage();
	}
}
