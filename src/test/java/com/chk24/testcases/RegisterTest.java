package com.chk24.testcases;

import java.sql.Timestamp;

import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.chk24.base.TestBase;
import com.chk24.pages.CustomerPage;
import com.chk24.pages.HomePage;
import com.chk24.pages.LoginPage;
import com.chk24.pages.RegisterPage;

public class RegisterTest extends TestBase{
	CustomerPage customerPage;
	HomePage homePage;
	RegisterPage registerPage;
	LoginPage loginPage;
	public RegisterTest(){
		super();
	}
	
	@BeforeSuite
	public void setUp(){
		initialization();
		registerPage = new RegisterPage();	
		customerPage = new CustomerPage();
		homePage = new HomePage();
		loginPage = new LoginPage();
	}
	
	@Test(priority=1)
	public void registerTitleTest(){
		homePage.clickOnNewCustomerLink();
		String headline = homePage.getRegisterPageHeading();
		Assert.assertEquals(headline, "Create CHECK24 customer account now and take advantage!");
	}
	
	@Test(priority=2)
	public void registerUserTest(){
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(timestamp.getTime());
        String email = "test" + timestamp.getTime() + "@email.com";
        prop.setProperty("username", email);
		registerPage.enterCustomerDetails(email, "Test@123");
		// Verifying if password is strong
		Assert.assertEquals(registerPage.validatePasswordStrength(), "strongly");
		registerPage.registerCustomer();
		customerPage.acceptWarningBonus();
		// Verifying Welcome Bonus pop up after successful register
		String loginText = loginPage.getLoginUserText();
		Assert.assertTrue(loginText.contains("You are logged in as " + email), "Registration failed");

	}	
	
	@AfterTest
	public void signOutApplication(){
		homePage.clickOnSignOut();
	}
	
	@AfterSuite
	public void tearDown(){
		driver.quit();
	}
}
