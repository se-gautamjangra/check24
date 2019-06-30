package com.chk24.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.chk24.base.TestBase;

public class RegisterPage extends TestBase{

	//Page Factory - OR:
		@FindBy(id="email")
		WebElement emailAddress;
		
		@FindBy(id="password")
		WebElement password;
		
		@FindBy(xpath="//div[@class='password-strength-container']//span[@id='indicator-text']")
		WebElement passwordIndicator;
		
		@FindBy(id="passwordrepetition")
		WebElement repeatPassword;
		
		@FindBy(name="register")
		WebElement registerButton;
		
		//Initializing the Page Objects:
		public RegisterPage(){
			PageFactory.initElements(driver, this);
		}
		
		public String validatePasswordStrength(){
			return passwordIndicator.getText();
		}
		
		public void enterCustomerDetails(String email,String pwd){
			emailAddress.sendKeys(email);
			password.sendKeys(pwd);
			repeatPassword.sendKeys(pwd);
		}

		public HomePage registerCustomer() {
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", registerButton);
			registerButton.click();
			waitForPageLoad();
			return new HomePage();
		}
		
		
}
