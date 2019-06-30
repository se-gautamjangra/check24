package com.chk24.testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.chk24.base.TestBase;
import com.chk24.pages.CustomerPage;
import com.chk24.pages.HomePage;
import com.chk24.pages.LoginPage;

public class LoginPageTest extends TestBase{
	LoginPage loginPage;
	HomePage homePage;
	CustomerPage customerPage;
	
	public LoginPageTest(){
		super();
	}
	
	@BeforeClass
	public void setUp(){
//		initialization();
		loginPage = new LoginPage();	
		homePage = new HomePage();
		customerPage = new CustomerPage();
	}
	
	@Test(priority=1)
	public void verifyHomePageTitleTest(){
		String homePageTitle = homePage.verifyHomePageTitle();
		Assert.assertTrue(homePageTitle.contains("CHECK24"), "Home page title not matched");
	}

	@Test(priority=2)
	public void loginPageTitleTest(){
		homePage.clickSignIn();
		String headline = homePage.getRegisterPageHeading();
		Assert.assertEquals(headline, "You have an account? Log in.");
	}
	
	@Test(priority=3)
	public void loginTest(){
		loginPage.login(prop.getProperty("email"), prop.getProperty("password"));
		customerPage.acceptWarningBonus();
		String loginText = loginPage.getLoginUserText();
		Assert.assertTrue(loginText.contains("You are logged in as " + prop.getProperty("email")), "Login failed");
	}
	
}
