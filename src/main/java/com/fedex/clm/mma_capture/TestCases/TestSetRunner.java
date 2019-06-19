package com.fedex.clm.mma_capture.TestCases;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import com.fedex.clm.mma_capture.Config.BrowserFactory;
import com.fedex.clm.mma_capture.Model.MMA_Model;
import com.fedex.clm.mma_capture.Pages.AccountSummary;
import com.fedex.clm.mma_capture.Pages.CheckOperations;
import com.fedex.clm.mma_capture.Pages.LogInError;
import com.fedex.clm.mma_capture.Pages.LogInPage;
import com.fedex.clm.mma_capture.Pages.SearchPage;
import com.fedex.clm.mma_capture.Pages.SearchResultPage;
import com.fedex.clm.mma_capture.Pages.TrackingDetailsPage2;
import com.fedex.clm.mma_capture.Pages.TranckingDetailsPage;
import com.fedex.clm.mma_capture.Pages.TranckingDetailsPage3;

public class TestSetRunner {

	public void executeTest(WebDriver driver,
			ArrayList<MMA_Model> mma_data_List, Set<String> badCustomerSet,
			Connection con, Properties propObjRepo, Properties propConfig,
			String threadName, Logger logger) throws InterruptedException,
			SQLException {

		String badCustomer = null;
		String previousCustomer = null;
		int count = 0;
		int i = 0;
		Iterator<MMA_Model> iteratorMMAData = mma_data_List.iterator();
		while (iteratorMMAData.hasNext()) {

			MMA_Model currentCustomer = iteratorMMAData.next();

			logger.info(threadName + " : Now Customer and tracking# is=>"
					+ count + "--" + currentCustomer.getCust_nbr() + "--"
					+ currentCustomer.getTrkng_nbr());
			count++;

			if (!(badCustomerSet.contains(currentCustomer.getCust_nbr()))) {

				if (i == 0) {

					driver.get(propConfig.getProperty("gfboGuiURL"));
					//driver.manage().window().maximize();
					// driver.manage().window().setPosition(new Point(-2000,0));

					i++;

					CheckOperations checkPageLoaded = PageFactory.initElements(
							driver, CheckOperations.class);

					if (checkPageLoaded.checkPageLoad(propObjRepo
							.getProperty("gfbo.mma.login.userID.xpath")) == false) {

						logger.error("GFBO Web Site is down :"
								+ propConfig.getProperty("gfboGuiURL"));
						System.exit(1);
					}

					LogInPage login_Page = PageFactory.initElements(driver,
							LogInPage.class);
					login_Page.login_mma(currentCustomer.getAccount_login_id(),
							currentCustomer.getAccount_login_password());
					Thread.sleep(1000);
				
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
						driver.manage().deleteAllCookies();
						Thread.sleep(500);
						driver.get(propConfig.getProperty("gfboGuiURL"));
						continue;

					}

					if (checkPageLoaded
							.checkPageLoad(propObjRepo
									.getProperty("gfbo.mma.search.accountSummary.xpath")) == false) {

						System.out.println("page not loaded after 20 seconds");
						System.exit(1);
					}

				} else if (!(currentCustomer.getCust_nbr()
						.equals(previousCustomer))) {

					CheckOperations checkPageLoaded = PageFactory.initElements(
							driver, CheckOperations.class);

					if (checkPageLoaded.checkPageLoad(propObjRepo
							.getProperty("gfbo.mma.summaryPage.logout.xpath")) == true) {

						driver.findElement(
								By.xpath(propObjRepo
										.getProperty("gfbo.mma.summaryPage.logout.xpath")))
								.click();
						
					}

					Thread.sleep(2000);
					
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
						// System.out.println("Bad Login in true");

						// Database call to update all row's to bad log in for
						// bad
						// customer
						
						login_Error.dbUpdateBadLogin(
								currentCustomer.getCust_nbr(), con, propConfig);
						badCustomerSet.add(badCustomer);
						logger.info("Updating as bad customer :"
								+ currentCustomer.getCust_nbr());
						//Thread.sleep(500);
						driver.manage().deleteAllCookies();
						Thread.sleep(500);
						continue;
					}

					if (checkPageLoaded
							.checkPageLoad(propObjRepo
									.getProperty("gfbo.mma.search.accountSummary.xpath")) == false) {

						logger.warn("Page not loaded error : Successful log-in but could not find navigation bar : Exiting 1");
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

				// Explicit wait to Check if page loaded to type tracking number

				CheckOperations checkPageLoaded = PageFactory.initElements(
						driver, CheckOperations.class);

				if (checkPageLoaded
						.checkPageLoad(propObjRepo
								.getProperty("gfbo.mma.search.searchTypeDropDown.xpath")) == false) {

					logger.error("Page not loaded error : To type tracking number after 20 seconds : Exiting 1");
					System.out
							.println("Page not loaded error : To type tracking number after 20 seconds : Exiting 1");
					//System.exit(1);
					driver.navigate().refresh();
				}
				
				Thread.sleep(1500);
				// select by tracking number and search

				SearchPage searchTrackingNbr = PageFactory.initElements(driver,
						SearchPage.class);
				searchTrackingNbr.searchTrackingNbr(currentCustomer
						.getTrkng_nbr());
				
				System.out.println("Tracking number is entered and clicked");

				// Explicit wait to Check if page loaded

			/*	if (checkPageLoaded.checkPageLoad("//span[contains(text(),'"
						+ currentCustomer.getTrkng_nbr() + "')]") == true) */
				
					if (checkPageLoaded.checkPageLoad(".//span[text()='Search results']/../../../../../../../../../../../tr[2]//a//span[@class='iceOutTxt link']") == true)
				{

					/*driver.findElement(
							By.xpath("//span[contains(text(),'"
									+ currentCustomer.getTrkng_nbr() + "')]"))
							.click();*/
						driver.findElement(
								By.xpath(".//span[text()='Search results']/../../../../../../../../../../../tr[2]//a//span[@class='iceOutTxt link']"))
								.click();

					// Explicit wait to Check if next page is loaded

					if (checkPageLoaded
							.checkPageLoad(propObjRepo
									.getProperty("gfbo.mma.summaryPage.details.charges.trasportationText.xpath")) == true) {

						previousCustomer = currentCustomer.getCust_nbr();

						TranckingDetailsPage3 detailsTracking = PageFactory
								.initElements(driver,
										TranckingDetailsPage3.class);

						List<String> queryList = detailsTracking
								.CatptureSenderReceipTable(driver,
										currentCustomer, propConfig, con);
					} else {
						logger.warn("Page not loaded error : account/tracking  details screen : Exiting 1");
						System.exit(1);

					}

				}

				
				else {
					System.out.println("tracking # not found");

					CheckOperations checktrackingNbr = PageFactory
							.initElements(driver, CheckOperations.class);

					if (checktrackingNbr.noTrackingResultcheck(currentCustomer,
							con, propConfig) == true) {

						logger.info("Tracking number not present=>"
								+ currentCustomer.getTrkng_nbr());

						previousCustomer = currentCustomer.getCust_nbr();

						continue;

					}
				}
			}
		}

	}

}
