package com.fedex.clm.mma_capture.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

public class AccountSummary {

	WebDriver driverAccountSummary;

	public AccountSummary(WebDriver driver) {

		this.driverAccountSummary = driver;
	}

	@FindBy(xpath = ".//li[@id='searchDownload']/a[1][contains (text(),'Search/Download')]")
	WebElement navigate_SearchDownloadLink;
	@FindBy(xpath = ".//*[@id='mainContentId:newSearchId']")
	WebElement select_NewSearchLink;

	public void navigateToSearch() throws InterruptedException{
		
		Actions builder=new Actions(driverAccountSummary);
		builder.moveToElement(navigate_SearchDownloadLink).click(select_NewSearchLink).perform();
		
	}
}
