package com.fedex.clm.mma_capture.TestExecutor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.fedex.clm.mma_capture.Config.BrowserFactory;
import com.fedex.clm.mma_capture.Config.ConnectionManager;
import com.fedex.clm.mma_capture.Config.DataBaseActivity;
import com.fedex.clm.mma_capture.Model.MMA_Model;
import com.fedex.clm.mma_capture.TestCases.TestSetRunner;

public class MainExecutor {
	

	public Connection con = null;
	Properties propConfig = new Properties();
	InputStream inputConfig = null;
	Properties propObjRepo = new Properties();
	InputStream inputObjRepo = null;

	ArrayList<MMA_Model> mma_data = new ArrayList<MMA_Model>();
	// ArrayList<MMA_Model> mma_data_List = new ArrayList<MMA_Model>();
	List<List<Integer>> mma_data_List = new LinkedList<List<Integer>>();
	ArrayList<List<MMA_Model>> finalList = new ArrayList<List<MMA_Model>>();
	ArrayList<MMA_Model> mma_data_List1 = new ArrayList<MMA_Model>();
	ArrayList<MMA_Model> mma_data_List2 = new ArrayList<MMA_Model>();
	ArrayList<MMA_Model> mma_data_List3 = new ArrayList<MMA_Model>();
	ArrayList<MMA_Model> mma_data_List4 = new ArrayList<MMA_Model>();
	ArrayList<MMA_Model> mma_data_List5 = new ArrayList<MMA_Model>();
	ArrayList<MMA_Model> mma_data_List6 = new ArrayList<MMA_Model>();
	ArrayList<MMA_Model> mma_data_List7 = new ArrayList<MMA_Model>();
	ArrayList<MMA_Model> mma_data_List8 = new ArrayList<MMA_Model>();
	ArrayList<MMA_Model> mma_data_List9 = new ArrayList<MMA_Model>();
	ArrayList<MMA_Model> mma_data_List10 = new ArrayList<MMA_Model>();
	Set<String> badCustomerSet = new HashSet<String>();
	
	private static final Logger logger1 = Logger.getLogger("set1Logger");
	
	

	@BeforeTest
	public void prepareExecution() throws IOException, SQLException {

		inputConfig = new FileInputStream(
				"config.properties");
		propConfig.load(inputConfig);
		inputObjRepo = new FileInputStream(
				"MMA_Obj_Repository.properties");
		propObjRepo.load(inputObjRepo);

		con = ConnectionManager.getConnection(propConfig);
		// Initial Transaction setup in database

		DataBaseActivity dbActivity = new DataBaseActivity();
		dbActivity.databaseInitialSetupCommit(con, propConfig);
		mma_data = dbActivity.databaseSelect(con, propConfig);
		Double partitionSize = ((double) mma_data.size() / 1);
		Double rounded = (double) Math.round(partitionSize);
		for (int i = 0; i < mma_data.size(); i += rounded) {
			finalList.add(mma_data.subList(i,
					(int) Math.min(i + rounded, mma_data.size())));
		}

		Iterator<List<MMA_Model>> itr = finalList.iterator();
		int ListSize = 1;

		while (itr.hasNext()) {

			ArrayList<MMA_Model> m = new ArrayList<MMA_Model>(itr.next());
			if (ListSize == 1) {
				mma_data_List1 = (ArrayList<MMA_Model>) m.clone();
				System.out.println("MMa size->" + mma_data_List1.size());
			}
			ListSize++;

		}
	}

	@Test
	public void Test_Set1()  {

		try {
		System.setProperty("webdriver.chrome.driver", "C:/chromedriver.exe");
		DesiredCapabilities cap=DesiredCapabilities.chrome();
		cap.setBrowserName("chrome");
		cap.setPlatform(Platform.WINDOWS);
		WebDriver driver1 = null;
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--start-maximized");
		driver1=new ChromeDriver();
		TestSetRunner runner1 = new TestSetRunner();
		String threadName="Set 1";
		System.out.println("MMA_DATA_SIZE :" + mma_data_List1.size());
		runner1.executeTest(driver1, mma_data_List1, badCustomerSet, con,
				propObjRepo, propConfig,threadName,logger1);
		driver1.close();
		}catch (Exception e) {
			System.out.println("Exception from main :"+e);
			e.printStackTrace();
		}

	}

}
