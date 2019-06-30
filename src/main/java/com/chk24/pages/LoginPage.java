package com.chk24.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.chk24.base.TestBase;

public class LoginPage extends TestBase{
	
	@FindBy(id="email")
	WebElement emailAddress;
	
	@FindBy(id="password")
	WebElement password;
	
	@FindBy(name="login")
	WebElement loginBtn;
	
	@FindBy(xpath="//div[@class='page-header-inner']/p/font/font")
	WebElement loginHeader;

	//Initializing the Page Objects:
	public LoginPage(){
		PageFactory.initElements(driver, this);
	}
	
	public String validateLoginPageTitle(){
		return driver.getTitle();
	}
	
	public HomePage login(String email, String pwd){
		emailAddress.clear();
		emailAddress.sendKeys(email);
		password.sendKeys(pwd);
		((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", loginBtn);
		loginBtn.click();
		
		return new HomePage();
	}
	
	public String getLoginUserText() {
		return loginHeader.getText();
	}
}
