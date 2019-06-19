package com.fedex.clm.mma_capture.Pages;

import java.sql.Connection;
import java.sql.PreparedStatement;
//import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.xpath.functions.Function2Args;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.NoSuchElementException;

import com.fedex.clm.mma_capture.Model.MMA_Model;


public class CheckOperations {

	WebDriver driverCheckOperations;

	public CheckOperations(WebDriver driver) {

		this.driverCheckOperations = driver;
	}

	public boolean checkPageLoad(String xpathAddress) {
		
		Boolean flg = false;
		
		try {
			
		

	/*	WebElement myDynamicElement = (new WebDriverWait(driverCheckOperations,
				5)).until(ExpectedConditions.presenceOfElementLocated(By
				.xpath(xpathAddress)));*/
				
		 Wait<WebDriver> fwait = new FluentWait <WebDriver>(driverCheckOperations)
				.withTimeout(15, TimeUnit.SECONDS)
				.pollingEvery(1, TimeUnit.SECONDS)
			    .ignoring(NoSuchElementException.class)
			    .ignoring(StaleElementReferenceException.class);
		 
		WebElement myDynamicElement = fwait.until(ExpectedConditions.presenceOfElementLocated(By
				.xpath(xpathAddress)));
		
	
		if (myDynamicElement.isDisplayed()) {
			flg=true;
			
		} 
	
		
		} catch (TimeoutException e) {
			flg=false;
			// TODO: handle exception			
			System.out.println("Time out Exception is "+e.getMessage());
			
		}  catch (Exception a) {
			
			System.out.println("Exception is "+a.getMessage());
			
		}
		 	
		return flg;
	
	}

	public Boolean noTrackingResultcheck(MMA_Model currentCustomer,
			Connection con, Properties propConfig) {

		try {
			//System.out.println("Checking for error message");
			WebElement myDynamicElement = (new WebDriverWait(
					driverCheckOperations, 3).until(ExpectedConditions
					.presenceOfElementLocated(By.className("errorMessage"))));

			if (myDynamicElement.isDisplayed()) {
				System.out.println("coming in first if");
				if (driverCheckOperations
						.findElement(By.className("errorMessage"))
						.getText()
						.equalsIgnoreCase(
								"There were no results. Please change your search criteria.")) {
					
					System.out.println("coming in second if");
					PreparedStatement statement = con
							.prepareStatement(propConfig
									.getProperty("update_Missing_Trkng"));
					System.out.println("=>"+currentCustomer.getTrkng_nbr());
					statement.setString(1, currentCustomer.getTrkng_nbr());
					statement.executeUpdate();
					con.commit();
					return true;
				} else {
					return false;
				}

			} else {
				return false;
			}
		}

		catch (Exception e) {


			return false;
		}

	}

}
