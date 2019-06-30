package com.chk24.testcases;

import java.sql.Timestamp;

import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.chk24.base.TestBase;
import com.chk24.pages.CustomerPage;
import com.chk24.pages.HomePage;
import com.chk24.pages.LoginPage;
import com.chk24.pages.RegisterPage;
import com.chk24.pages.TravelPage;

public class TravelTest extends TestBase{
	LoginPage loginPage;
	HomePage homePage;
	CustomerPage customerPage;
	TravelPage travelPage;
	RegisterPage registerPage;
	
	public TravelTest(){
		super();
	}
	
	@BeforeSuite
	public void setUp(){
		initialization();
		loginPage = new LoginPage();	
		homePage = new HomePage();
		customerPage = new CustomerPage();
		travelPage = new TravelPage();
		registerPage = new RegisterPage();	}
	
	@Test(priority=1)
	public void check24TitleTest(){
		homePage.clickOnNewCustomerLink();
		String headline = homePage.getRegisterPageHeading();
		Assert.assertEquals(headline, "Create CHECK24 customer account now and take advantage!");
	}
	
	@Test(priority=2)
	public void registerUserTest(){
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp.getTime());
        String email = "test" + timestamp.getTime() + "@email.com";
        prop.setProperty("email", email);
		registerPage.enterCustomerDetails(email, "Test@123");
		// Verifying if password is strong
		Assert.assertEquals(registerPage.validatePasswordStrength(), "strongly");
		registerPage.registerCustomer();
		customerPage.acceptWarningBonus();
		// Verifying successful register
		String loginText = loginPage.getLoginUserText();
		Assert.assertTrue(loginText.contains("You are logged in as " + email), "Registration failed");
		homePage.clickOnSignOut();
	}	

	@Test(priority=3)
	public void loginTest(){
		homePage.clickSignIn();
		String headline = homePage.getRegisterPageHeading();
		Assert.assertEquals(headline, "You have an account? Log in.");
		loginPage.login(prop.getProperty("email"), prop.getProperty("password"));
		customerPage.acceptWarningBonus();
		String loginText = loginPage.getLoginUserText();
		Assert.assertTrue(loginText.contains("You are logged in as " + prop.getProperty("email")), "Login failed");
	}
	
	@Test(priority=4)
	public void findATripTest(){
		homePage.clickTravelTab();		
		String headline = travelPage.getTravelHeadline();
		Assert.assertTrue(headline.contains("Holiday travel"));
		travelPage.searchYourTrip("Majorca", "Hamburg", 1, 1, 1, "01-07-2019");
		// Verifying search results > 0
		Assert.assertTrue(travelPage.getResultsCount() > 0, "No Result Found");
//		Assert.assertTrue(travelPage.getResultsHeadline().contains("Majorca"), "No Result found with given destination");
	}

}
