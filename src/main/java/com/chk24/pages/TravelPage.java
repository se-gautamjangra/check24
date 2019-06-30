package com.chk24.pages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.chk24.base.TestBase;

public class TravelPage extends TestBase {

	@FindBy(xpath = "//div[@class='c24-travel-headline-searchform']/h1")
	WebElement travelHeadline;

	@FindBy(name = "destination")
	WebElement destination;

	@FindBy(name = "airport-element")
	WebElement departureAirport;

	@FindBy(id = "c24-travel-traveller-roomcount-detail-btn")
	WebElement travellers;

	@FindBy(xpath = "//input[@type='submit' and @value='Save']")
	WebElement saveBtn;

	@FindBy(xpath="//select[@id='c24-travel-travel-duration-element']")
	WebElement travelDuration;
	
	@FindBy(id = "c24-travel-js-get-traveller-btn")
	WebElement travellersTakeOver;

	@FindBy(name = "departureDate")
	WebElement departureDate;

	@FindBy(name = "returnDate")
	WebElement returnDate;

	@FindBy(name = "searchButton")
	WebElement findTrip;
	
	@FindAll({@FindBy(xpath="//div[@class='region-name']")})
	List<WebElement> regions;

	@FindAll({@FindBy(xpath="//span[@class='price-info']")})
	List<WebElement> tripDetails;
	
	@FindAll({@FindBy(xpath="//div[@id='js-hotel-list-holder']/ul/li")})
	List<WebElement> tripResults;
	
	@FindBy(xpath="//div[contains(@class,'headline-hotel')]/h3")
	WebElement tripResultsHeadline;
	
	@FindBy(xpath="//div[contains(@class,'js-headline-multiple-hotels')]/span[contains(@class,'js-deferred-count')]")
	WebElement tripResultsCount;
	
	@FindBy(xpath="//span[contains(@class,'c24-travel-delete-child-selection')]")
	WebElement removeChild;
	
	public TravelPage() {
		PageFactory.initElements(driver, this);
	}

	public String getTravelHeadline() {
		return travelHeadline.getText();
	}

	public void selectTravellers(int adults, int children) {

		String adultXpath = "//span[@id='c24-travel-js-adult-select-number']";
		String childrenXpath = "//span[@id='c24-travel-js-child-select-number']";
		travellers.click();
		if (adults <= 6) {
			adultXpath = adultXpath.replace("number", String.valueOf(adults));
		} else {
			adultXpath = adultXpath.replace("number", "7");
		}
		driver.findElement(By.xpath(adultXpath)).click();
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView(true);", travellers);

		if (children > 0) {
			childrenXpath = childrenXpath.replace("number",
					String.valueOf(children));
			if(removeChild.isDisplayed())
				removeChild.click();
			driver.findElement(By.xpath(childrenXpath)).click();
		}
		setChildAge(1, 4);
		travellersTakeOver.click();
	}

	public void setChildAge(int childNo, int age) {
		String child = "//span[contains(@class,'c24-travel-js-child-age-item-"
				+ String.valueOf(childNo)
				+ "')]//";
		String incChildAge = child + "span[@class='c24-travel-older']";
		for (int i = 0; i < age; i++) {
			driver.findElement(By.xpath(incChildAge)).click();
		}
	}

	public void selectDurationInWeek(int weeks, String depDate) {
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView(true);", travellers);

		String duration = "//label[@for='c24-travel-duration-1']";
		SimpleDateFormat sdf = new SimpleDateFormat("dd-M-yyyy");
		Date currentDate = new Date();
		try {
			currentDate = sdf.parse(depDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(currentDate);
		String dateTime = String.valueOf(cal.getTimeInMillis());
		String startDate = "//div[@data-time='" + dateTime + "']";
		Calendar returnCal = cal;
		// driver.findElement(By.xpath(startDate)).click();
		String returnDate = "";
		if (weeks == 1) {
			duration = "//label[@for='c24-travel-duration-2']";
			returnCal.add(Calendar.DAY_OF_MONTH, 7);
			String returnTime = String.valueOf(cal.getTimeInMillis());
			returnDate = "//div[@data-time='" + returnTime + "']";
		} else if (weeks == 2) {
			duration = "//label[@for='c24-travel-duration-3']";
			returnCal.add(Calendar.DAY_OF_MONTH, 14);
			String returnTime = String.valueOf(cal.getTimeInMillis());
			returnDate = "//div[@data-time='" + returnTime + "']";
		} else {
			duration = "c24-travel-duration-" + 1;
		}
		if(!driver.findElement(By.xpath(duration)).isDisplayed()) {
			Actions actions = new Actions(driver);
			actions.moveToElement(travelDuration).click().build().perform();
		}
		driver.findElement(By.xpath(duration)).click();
		Actions actions = new Actions(driver);
		actions.moveToElement(departureDate).click().build().perform();
		driver.findElement(By.xpath(startDate)).click();
		driver.findElement(By.xpath(returnDate)).click();
		((JavascriptExecutor) driver).executeScript(
				"arguments[0].scrollIntoView(true);", travellers);
		 findTrip.click();

	}

	public void searchYourTrip(String dest, String airport, int adults,
			int children, int weeks, String depDate) {
		String autoComplete = "//a[@title='Majorca, Spain (1914 Hotels)']/parent::li";
		destination.sendKeys(dest);
		waitForSeconds(3000);
		driver.findElement(By.xpath(autoComplete)).click();
		pressTab();
		departureAirport.sendKeys(airport);
		pressTab();
		selectTravellers(adults, children);
		selectDurationInWeek(weeks, depDate);
		waitForPageLoad();
	}

	public List<String> getSearchedDestinations() {
		List<String> destinations = new ArrayList<String>();
		for (WebElement region : regions) {
			destinations.add(region.getText());
		}
		System.out.println("destinations = " + destinations);
		return destinations;
	}
	
	public List<String> getSearchedResults() {
		List<String> results = new ArrayList<String>();
		for (WebElement details : tripDetails) {
			results.add(details.getText());
		}
		System.out.println("results = " + results);
		return results;
	}

	public int getResultsCount() {
		System.out.println("Result Count - " + tripResultsCount.getText().trim());
		return Integer.parseInt(tripResultsCount.getText().trim());
	}
	
	public String getResultsHeadline() {
		System.out.println("Result Headline - " + tripResultsHeadline.getText());
		return tripResultsHeadline.getText();
	}
	
	private void pressTab() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Actions builder = new Actions(driver);
		builder.sendKeys(Keys.TAB).build().perform();

	}
	
	private void waitForSeconds(int sec) {
		try {
			Thread.sleep(sec);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
