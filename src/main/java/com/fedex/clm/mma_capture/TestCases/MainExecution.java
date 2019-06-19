package com.fedex.clm.mma_capture.TestCases;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.jboss.netty.handler.queue.BufferedWriteHandler;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.fedex.clm.mma_capture.Config.BrowserFactory;
import com.fedex.clm.mma_capture.Config.ConnectionManager;
import com.fedex.clm.mma_capture.Config.DataBaseActivity;
import com.fedex.clm.mma_capture.Model.MMA_Model;
import com.fedex.clm.mma_capture.Pages.AccountSummary;
import com.fedex.clm.mma_capture.Pages.CheckOperations;
import com.fedex.clm.mma_capture.Pages.LogInError;
import com.fedex.clm.mma_capture.Pages.LogInPage;
import com.fedex.clm.mma_capture.Pages.SearchPage;
import com.fedex.clm.mma_capture.Pages.SearchResultPage;
import com.fedex.clm.mma_capture.Pages.TrackingDetailsPage2;

public class MainExecution {

	public Connection con = null;
	Properties propConfig = new Properties();
	InputStream inputConfig = null;
	Properties propObjRepo = new Properties();
	InputStream inputObjRepo = null;
	ArrayList<MMA_Model> mma_data = new ArrayList<MMA_Model>();
	Set<String> badCustomerSet = new HashSet<String>();

	@BeforeTest
	public void prepareExecution() throws IOException, SQLException {

		inputConfig = new FileInputStream(
				"src/main/resources/config.properties");
		propConfig.load(inputConfig);
		inputObjRepo = new FileInputStream(
				"src/main/resources/MMA_Obj_Repository.properties");
		propObjRepo.load(inputObjRepo);

		con = ConnectionManager.getConnection(propConfig);
		System.out.println("Got Connection:" + con);

		// Initial Transaction setup in database

		DataBaseActivity dbInitialActivity = new DataBaseActivity();
		Boolean updateReturn = dbInitialActivity.databaseInitialSetupCommit(
				con, propConfig);
		System.out.println("Database setup return is ==>" + updateReturn);

		DataBaseActivity dbSelectToProcess = new DataBaseActivity();

		mma_data = dbSelectToProcess.databaseSelect(con, propConfig);

		System.out.println("Got MMA data size-->" + mma_data.size());

	}

	@SuppressWarnings("unchecked")
	@Test
	public void startExecution() throws InterruptedException, SQLException, IOException {
		WebDriver driver = null;
		String badCustomer = null;
		String previousCustomer = null;

		Iterator<MMA_Model> iteratorMMAData = mma_data.iterator();
		@SuppressWarnings("unused")
		int count=0;
		int i = 0;

		while (iteratorMMAData.hasNext()) {

			MMA_Model currentCustomer = iteratorMMAData.next();

		
			System.out.println("Now Customer and tracking# is=>"+count+"--"+ currentCustomer.getCust_nbr()+"--"+ currentCustomer.getTrkng_nbr());
			
			count++;		
			
			System.out.println("Is Bad custoer =>"+badCustomerSet.contains(currentCustomer.getCust_nbr()));
			
			if (!(badCustomerSet.contains(currentCustomer.getCust_nbr()))) {
			
				if (i == 0) {
					driver = BrowserFactory.startBrowser("chrome",
							propConfig.getProperty("gfboGuiURL"));
					//System.setProperty("webdriver.chrome.driver", "C:/chromedriver.exe");
					/*DesiredCapabilities cap=DesiredCapabilities.chrome();
					cap.setBrowserName("chrome");
					cap.setPlatform(Platform.WINDOWS);
					try {
						driver=new RemoteWebDriver(new URL("http://199.82.27.202:5558/wd/hub"),cap);
						driver.get(propConfig.getProperty("gfboGuiURL"));
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}*/
					
					i++;
					
					CheckOperations checkPageLoaded = PageFactory.initElements(driver,
							CheckOperations.class);

					if (checkPageLoaded.checkPageLoad(propObjRepo
							.getProperty("gfbo.mma.login.userID.xpath")) == false) {

						System.out.println("page not loaded after 20 seconds");
						System.exit(1);
					}

					LogInPage login_Page = PageFactory.initElements(driver,
							LogInPage.class);
					login_Page.login_mma(currentCustomer.getAccount_login_id(),
							currentCustomer.getAccount_login_password());

					LogInError login_Error = PageFactory.initElements(driver,
							LogInError.class);

					if (login_Error.checkBadLogin() == true) {
						System.out.println("Bad Login in true");

						// Database call to update all row's to bad log in for
						// bad
						// customer

						login_Error.dbUpdateBadLogin(
								currentCustomer.getCust_nbr(), con, propConfig);
						badCustomerSet.add(badCustomer);
						driver.get(propConfig.getProperty("gfboGuiURL"));
						continue;

					}
					
					if (checkPageLoaded.checkPageLoad(propObjRepo
							.getProperty("gfbo.mma.search.accountSummary.xpath")) == false) {

						System.out.println("page not loaded after 20 seconds");
						System.exit(1);
					}

				} else if (!(currentCustomer.getCust_nbr().equals(previousCustomer))) {
					
					
					CheckOperations checkPageLoaded = PageFactory.initElements(driver,
							CheckOperations.class);
					
					if (checkPageLoaded.checkPageLoad(propObjRepo
							.getProperty("gfbo.mma.summaryPage.logout.xpath")) == true) {

						driver.findElement(By.xpath(propObjRepo.getProperty("gfbo.mma.summaryPage.logout.xpath"))).click();
						
					}
					
					
					
					Thread.sleep(3000);
					
					
					
					driver.get(propConfig.getProperty("gfboGuiURL"));

					if (checkPageLoaded.checkPageLoad(propObjRepo
							.getProperty("gfbo.mma.login.userID.xpath")) == false) {

						System.out.println("page not loaded after 20 seconds");
						System.exit(1);
					}

					LogInPage login_Page = PageFactory.initElements(driver,
							LogInPage.class);
					login_Page.login_mma(currentCustomer.getAccount_login_id(),
							currentCustomer.getAccount_login_password());

					LogInError login_Error = PageFactory.initElements(driver,
							LogInError.class);

					if (login_Error.checkBadLogin() == true) {
						System.out.println("Bad Login in true");

						// Database call to update all row's to bad log in for
						// bad
						// customer

						login_Error.dbUpdateBadLogin(
								currentCustomer.getCust_nbr(), con, propConfig);
						badCustomerSet.add(badCustomer);

						continue;
					}
					
					if (checkPageLoaded.checkPageLoad(propObjRepo
							.getProperty("gfbo.mma.search.accountSummary.xpath")) == false) {

						System.out.println("page not loaded after 20 seconds");
						System.exit(1);
					}
				}

				// Calling explicit wait to load page and check Account Summary
				// is
				// present

				

				// On account Summary page selecting New Search from drop down
				// Search/Download

				AccountSummary account_sum_Navigate = PageFactory.initElements(
						driver, AccountSummary.class);
				account_sum_Navigate.navigateToSearch();
				
				Thread.sleep(1000);

				// Explicit wait to Check if page loaded to type tracking number

				CheckOperations checkPageLoaded = PageFactory.initElements(driver,
						CheckOperations.class);
				
				if (checkPageLoaded
						.checkPageLoad(propObjRepo
								.getProperty("gfbo.mma.search.searchTypeDropDown.xpath")) == false) {

					System.out.println("page not loaded to type tracking number after 20 seconds");
					System.exit(1);
				}

				// select by tracking number and search

				SearchPage searchTrackingNbr = PageFactory.initElements(driver,
						SearchPage.class);
				searchTrackingNbr.searchTrackingNbr(currentCustomer
						.getTrkng_nbr());


				// Checking if no Tracking number found and its db update

				CheckOperations checktrackingNbr = PageFactory.initElements(
						driver, CheckOperations.class);
				if (checktrackingNbr.noTrackingResultcheck(currentCustomer,
						con, propConfig) == true) {

					System.out.println("Tracking number not present");

					previousCustomer = currentCustomer.getCust_nbr();

					continue;
				}

				// Explicit wait to Check if page loaded

				if (checkPageLoaded.checkPageLoad(propObjRepo
						.getProperty("gfbo.mma.search.result.xpath")) == false) {

					System.out.println("page not loaded after 20 seconds");
					System.exit(1);
				}

				// on Search Result Page clicking on tracking#
				SearchResultPage clickTrackingResult = PageFactory
						.initElements(driver, SearchResultPage.class);
				clickTrackingResult.clickTrackingNbrLink();

				// Explicit wait to Check if page loaded

				if (checkPageLoaded
						.checkPageLoad(propObjRepo
								.getProperty("gfbo.mma.summaryPage.details.charges.trasportationText.xpath")) == false) {

					System.out.println("page not loaded after 20 seconds");
					// break;
				}

				previousCustomer = currentCustomer.getCust_nbr();

				TrackingDetailsPage2 detailsTracking2 = PageFactory
						.initElements(driver, TrackingDetailsPage2.class);
				List<String> queryList = detailsTracking2
						.CatptureSenderReceipTable(driver, currentCustomer,propConfig);
				
				driver.findElement(By.tagName("body")).getText().contains("Tender date");
				/*BufferedWriter out = new BufferedWriter(new FileWriter("C:/filename.txt"));
				out.write(driver.findElement(By.tagName("body")).getText());
				out.close();*/
				
				Iterator itr = queryList.iterator();
			//	System.out.println("Iterator length=>" + queryList.size());
				while (itr.hasNext()) {

					String p = (String) itr.next();
					//System.out.println("Query is =>" + p);
					try {

						//System.out.println("coonection=>" + con);
						PreparedStatement statement1 = con.prepareStatement(p);
						statement1.executeUpdate();
						//System.out.println("Inserted -->" + p);
						statement1.close();
					} catch (Exception e) {

						System.out
								.println("Exception coming from insert->" + e);
					}

				}
				con.commit();

				// System.out.println("Value of i = >" + i);

			}
		}
	}

}
