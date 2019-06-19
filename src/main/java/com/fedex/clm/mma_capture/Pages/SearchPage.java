package com.fedex.clm.mma_capture.Pages;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SearchPage {
	
	WebDriver driverSearchPage;

	public SearchPage(WebDriver driver) {

		this.driverSearchPage = driver;
	}
	
	@FindBy(xpath=".//*[@id='mainContentId:quickTempl']") WebElement searchDropDownList;
	@FindBy(xpath="//input[@value='Quick Search']/../../../../tr[4]//input") WebElement inputTrackingNbrBox;
	@FindBy(xpath="//*[@id='mainContentId:simpleSearch1']") WebElement quickSearchButton;
	
	
	public void searchTrackingNbr(String trackingNbr) throws InterruptedException{
			
		/*WebElement myDynamicElement = (new WebDriverWait(driverSearchPage,
				20)).until(ExpectedConditions.presenceOfElementLocated(By
				.xpath(".//*[@id='mainContentId:quickTempl']")));*/
		//System.out.println("Dynamic web element check in selecting drop down r==>" + myDynamicElement);
		System.out.println("Entering the tracking#");
		 Wait<WebDriver> fwait = new FluentWait <WebDriver>(driverSearchPage)
					.withTimeout(8, TimeUnit.SECONDS)
					.pollingEvery(1, TimeUnit.SECONDS)
				    .ignoring(NoSuchElementException.class)
				    .ignoring(StaleElementReferenceException.class);
		 
			WebElement myDynamicElement = fwait.until(ExpectedConditions.presenceOfElementLocated(By
					.xpath(".//*[@id='mainContentId:quickTempl']")));
			
		if (myDynamicElement.isEnabled()) {
			Select trackID = new Select(myDynamicElement);
			trackID.selectByVisibleText("Tracking ID");
		}
		
		/*WebElement myDynamicElement1 = (new WebDriverWait(driverSearchPage,
				20)).until(ExpectedConditions.presenceOfElementLocated(By
				.xpath("//input[@value='Quick Search']/../../../../tr[4]//input")));*/
		//System.out.println("Dynamic web element check in tracking number editbox ==>" + myDynamicElement1);
		
		WebElement myDynamicElement1 = fwait.until(ExpectedConditions.presenceOfElementLocated(By
				.xpath("//input[@value='Quick Search']/../../../../tr[4]//input")));
		
		
		if (myDynamicElement1.isEnabled()) {
			myDynamicElement1.clear();
			myDynamicElement1.sendKeys(trackingNbr);
		}
		
		myDynamicElement1.sendKeys(Keys.TAB);
				
		WebElement myDynamicElement2 = (new WebDriverWait(driverSearchPage,
				20)).until(ExpectedConditions.elementToBeClickable(By
				.xpath("//*[@id='mainContentId:simpleSearch1']")));		
		//System.out.println("Dynamic web element check in selecting drop down and entering tracking number==>" + myDynamicElement2);
		
		/*WebElement myDynamicElement2 = fwait.until(ExpectedConditions.elementToBeClickable(By
				.xpath("//*[@id='mainContentId:simpleSearch']")));*/
		
			myDynamicElement2.click();
		
		
	}

}
