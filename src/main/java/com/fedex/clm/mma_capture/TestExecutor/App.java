package com.fedex.clm.mma_capture.TestExecutor;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;

import org.testng.TestListenerAdapter;
import org.testng.TestNG;
import org.testng.collections.Lists;

public class App {

	public static void main(String[] args) throws IOException {
		
		TestListenerAdapter tla = new TestListenerAdapter();
		TestNG testng = new TestNG();
		List<String> suites = Lists.newArrayList();
		suites.add("testng.xml");//path to xml..
		testng.setTestSuites(suites);
		testng.run();
		
	}

}


