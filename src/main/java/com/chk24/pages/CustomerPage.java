package com.chk24.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.chk24.base.TestBase;

public class CustomerPage extends TestBase{

	@FindBy(id="termsaccepted")
	WebElement acceptTerms;
	
	@FindBy(className="points_register")
	WebElement submitWelcomePoints;
	
	@FindBy(xpath="//img[@title='Welcome points']/following-sibling::span/font/font")
	WebElement welcomeBonusPoints;
	
	//Initializing the Page Objects:
	public CustomerPage(){
		PageFactory.initElements(driver, this);
	}
	public String getWelcomeBounsHeading() {
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img[@title='Welcome points']/following-sibling::span/font/font")));
		String welcomeBonusText = welcomeBonusPoints.getText();
		welcomeBonusPoints.sendKeys(Keys.ESCAPE);
		return welcomeBonusText;
	}
	
	public void acceptWarningBonus() {
		acceptTerms.sendKeys(Keys.ESCAPE);
	}
}
