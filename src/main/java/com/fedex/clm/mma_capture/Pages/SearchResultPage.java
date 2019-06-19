package com.fedex.clm.mma_capture.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchResultPage {
	
	WebDriver driverSearchResultPage;

	public SearchResultPage(WebDriver driver) {

		this.driverSearchResultPage = driver;
	}

	@FindBy(xpath=".//table[@id='mainContentId:trkAdvSrchRsltTable']/tbody/tr/td[2]//span") WebElement clickTrackingNbr;
	
	public void clickTrackingNbrLink(){
		
		clickTrackingNbr.click();
		
	}
}
