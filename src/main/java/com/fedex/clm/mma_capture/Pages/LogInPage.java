package com.fedex.clm.mma_capture.Pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LogInPage {

	WebDriver driverLogInPage;

	public LogInPage(WebDriver driver) {

		this.driverLogInPage = driver;
	}

	@FindBy(name = "username")
	WebElement username;
	@FindBy(name = "password")
	WebElement password;
	@FindBy(name = "login")
	WebElement login;

	public void login_mma(String accountNo, String accountPassword) {

		username.sendKeys(accountNo);
		password.sendKeys(accountPassword);
		login.click();
	}

}
