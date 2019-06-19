package com.fedex.clm.mma_capture.Config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class BrowserFactory {
	
	static WebDriver driver;

	public static WebDriver startBrowser(String browserName, String url) {

	/*	if (browserName.equalsIgnoreCase("IE")) {
			
			System.setProperty("webdriver.ie.driver", "C:/IEDriverServer.exe");
			
			DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();
			capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
			capabilities.setCapability("requireWindowFocus", true);
			
			driver=new InternetExplorerDriver(capabilities);

		} else if (browserName.equalsIgnoreCase("firefox")) {
			
			driver= new FirefoxDriver();

		} else if (browserName.equalsIgnoreCase("chrome")) {
			
			System.setProperty("webdriver.chrome.driver", "C:/chromedriver.exe");
			driver=new ChromeDriver();
		}*/
		
		driver.manage().window().maximize();
		driver.get(url);
		return driver;
	}

}
