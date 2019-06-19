package com.fedex.clm.mma_capture.Pages;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LogInError {

	WebDriver driverLogInError;

	public LogInError(WebDriver driver) {

		this.driverLogInError = driver;
	}

	@FindBy(xpath = ".//*[@id='content']/div/form/table/tbody/tr[1]/td[1]/table/tbody/tr[1]/td/table/tbody/tr[1]/td")
	WebElement badLoginText;

	public Boolean checkBadLogin() {

		try {
			
			WebElement myDynamicElement = (new WebDriverWait(driverLogInError,
					3)).until(ExpectedConditions.presenceOfElementLocated(By
					.xpath(".//*[@id='content']/div/form/table/tbody/tr[1]/td[1]/table/tbody/tr[1]/td/table/tbody/tr[1]/td")));
			//System.out.println("Dynamic web element check==>" + myDynamicElement);
			if (myDynamicElement.getText().equalsIgnoreCase("fedex.com Login Registration")) {
				return true;
			} else {
				return false;
			}

			
		} catch (Exception e) {

			/*System.out.println("Element not found in bad login check" + e);*/
			return false;
		}

	}
	
	public void dbUpdateBadLogin(String badCustomer, Connection con, Properties propConfig){
		
		try {
			
			PreparedStatement statement=con.prepareStatement(propConfig.getProperty("update_Invalid_Login"));
			statement.setString(1, badCustomer);
			statement.executeUpdate();	
			con.commit();
			
		} catch (Exception e) {
			System.out.println("Exception coming from updataing bad customer records =>"+e);
		}
		
		
	}

}
