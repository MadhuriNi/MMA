package com.fedex.clm.mma_capture.Pages;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.server.handler.FindElement;
import org.openqa.selenium.support.FindBy;

import com.fedex.clm.mma_capture.Model.MMA_Model;

public class TrackingDetailsPage2 {

	String sndrUpdate = "";
	String receipUpdate = "";
	String shipUpdate1 = "";
	String chargeUpdate = "";
	String deliveryUpdate = "";
	String uRefUpdate = "";
	String oRefUpdate = "";
	String shpDetUpdate = "";
	String messageUpdate = "";

	WebDriver driverTranckingDetailsPage;

	public void TranckingDetailsPage(WebDriver driver) {

		this.driverTranckingDetailsPage = driver;
	}

	@FindBy(xpath = ".//*[@id='mainContentId:j_id279']")
	WebElement tableSenderElement;
	@FindBy(xpath = ".//*[@id='mainContentId:j_id292']")
	WebElement tableReceipElement;

	@SuppressWarnings("unchecked")
	public List<String> CatptureSenderReceipTable(WebDriver driver,
			MMA_Model currentCustomer, Properties propConfig) {

		WebElement[] webList = new WebElement[11];

		webList[0] = tableSenderElement;
		webList[1] = tableReceipElement;
		int it = 2;

		String[] findTable = new String[8];

		findTable[0] = "Invoice no.";
		findTable[1] = "Payment type";
		findTable[2] = "Transportation Charge";
		findTable[3] = "Total charges";
		findTable[4] = "Delivery date";
		findTable[5] = "Cost allocation";
		findTable[6] = "Customer reference no.";

		List<String> tableActualId = new ArrayList<String>();

		List<String> insertQuery = new ArrayList<String>();

		String updateSQL = "UPDATE mma_work_table SET RSLT_SUBTYPE ='STATUS' , RSLT_VALUE = 'PASS', RSLT_TYPE = 'GFBO' ,THREAD_RECORD_STATUS='C' WHERE CUST_NBR = '"
				+ currentCustomer.getCust_nbr()
				+ "' AND TRKNG_NBR = '"
				+ currentCustomer.getTrkng_nbr()
				+ "' and RSLT_SUBTYPE='null' and RSLT_VALUE='null' and RSLT_TYPE='null'";

		insertQuery.add(updateSQL);

		for (int p = 0; p < findTable.length; p++) {

			Object[] arr = new Object[] {};

			List<Object> list = Arrays.asList(arr);

			list = (List<Object>) ((JavascriptExecutor) driver)
					.executeScript(""
							+ "var spans = document.getElementsByTagName('span');"
							+ "var tableID = [];"
							+ "for(i=0;i<spans.length;i++){"
							+ "if(spans[i].innerHTML=='"
							+ findTable[p]
							+ "'){"
							+ "tableID[i] = (spans[i].closest('table').getAttribute('id')).toString();"
							+ "console.log(tableID[i]);}" + "}"
							+ "return tableID;"

							+ "");

			Iterator itr = list.iterator();

			while (itr.hasNext()) {

				String a = (String) itr.next();
				if (a != null) {
				//	System.out.println(findTable[p] + "table id's is==>" + a);
					tableActualId.add(a);
				}
			}
		}

		Iterator itr = tableActualId.iterator();

		while (itr.hasNext()) {

			String a = (String) itr.next();
			String xp = ".//*[@id='" + a + "']";
			webList[it] = driver.findElement(By.xpath(xp));

			it++;
		}

		for (int i = 0; i < webList.length; i++) {
			/*System.out.println("In tracking Details page 2");
			System.out.println("Now Web List Iteam is ==>" + webList[i]);
			System.out
					.println("*************************************WebList size is "
							+ webList.length);
			System.out.println("Iteration number==>" + i);*/
			if (webList[i] != null) {
				List<WebElement> rows = webList[i].findElements(By
						.tagName("tr"));
				//System.out.println("Now Row's are=>" + rows.size());
				for (int rnum = 0; rnum < rows.size(); rnum++) {
					List<WebElement> columns = rows.get(rnum).findElements(
							By.tagName("td"));

				//	System.out.println("Now Column is=>" + columns.size());
					for (int cnum = 0; cnum < columns.size(); cnum++) {

						if (i == 0) {

							String col_Val = "";
							if (columns.get(cnum).getText().trim().length() > 0) {
								col_Val = columns.get(cnum).getText().trim();
							} else {
								col_Val = "null";
							}

							sndrUpdate = "insert into mma_work_table (JOB_ID, DATA_TYPE, TEST_ID, CUST_NBR, INV_NBR, TRKNG_NBR, RSLT_SUBTYPE, RSLT_VALUE, RSLT_TYPE, SHIP_DATE, DEVICE, SHP_SEQ_NBR) values ("
									+ "'"
									+ currentCustomer.getJob_id()
									+ "','"
									+ currentCustomer.getData_type()
									+ "','"
									+ currentCustomer.getTest_id()
									+ "','"
									+ currentCustomer.getCust_nbr()
									+ "','"
									+ currentCustomer.getInv_nbr()
									+ "','"
									+ currentCustomer.getTrkng_nbr()
									+ "','Line"
									+ (rnum + 1)
									+ "','"
									+ col_Val
									+ "','SNDR','"
									+ currentCustomer.getShip_date()
									+ "','"
									+ currentCustomer.getDevice()
									+ "','"
									+ currentCustomer.getShp_seq_nbr() + "')";

							/*System.out.println("Sender Info update sql  =>"
									+ sndrUpdate);*/

							insertQuery.add(sndrUpdate);

						}

						if (i == 1) {

							String col_Val = "";
							if (columns.get(cnum).getText().trim().length() > 0) {
								col_Val = columns.get(cnum).getText().trim();
							} else {
								col_Val = "null";
							}

							String lineWord = "";

							if ((rnum + 1) == 1) {
								lineWord = "RECPNM";
							}
							if ((rnum + 1) == 2) {
								lineWord = "RECPCONM";
							}
							if ((rnum + 1) == 3) {
								lineWord = "RECPADDRLN1";
							}
							if ((rnum + 1) == 4) {
								lineWord = "RECPADDRLN2";
							}
							if ((rnum + 1) == 5) {
								lineWord = "RECPCITYSTZIP";
							}
							if ((rnum + 1) == 6) {
								lineWord = "RECPCNTRYCD";
							}

							receipUpdate = "insert into mma_work_table (JOB_ID, DATA_TYPE, TEST_ID, CUST_NBR, INV_NBR, TRKNG_NBR, RSLT_SUBTYPE, RSLT_VALUE, RSLT_TYPE, SHIP_DATE, DEVICE, SHP_SEQ_NBR) values ("
									+ "'"
									+ currentCustomer.getJob_id()
									+ "','"
									+ currentCustomer.getData_type()
									+ "','"
									+ currentCustomer.getTest_id()
									+ "','"
									+ currentCustomer.getCust_nbr()
									+ "','"
									+ currentCustomer.getInv_nbr()
									+ "','"
									+ currentCustomer.getTrkng_nbr()
									+ "','"
									+ lineWord
									+ "','"
									+ col_Val
									+ "','RECP','"
									+ currentCustomer.getShip_date()
									+ "','"
									+ currentCustomer.getDevice()
									+ "','"
									+ currentCustomer.getShp_seq_nbr() + "')";

							/*System.out.println("Receip Info update sql  =>"
									+ receipUpdate);*/

							insertQuery.add(receipUpdate);

						}

						// System.out.println(columns.get(cnum).getText());
					}

					if (i == 3 && columns.size() == 2) {

						if (!driver.findElement(By.tagName("body")).getText()
								.contains("Tendered date")) {

							/*System.out.println("Data Column1=>"
									+ columns.get(0).getText().trim() + "-"
									+ columns.get(1).getText().trim()); // +"-"+columns.get(2).getText().trim());
*/
							shpDetUpdate = "insert into mma_work_table (JOB_ID, DATA_TYPE, TEST_ID, CUST_NBR, INV_NBR, TRKNG_NBR, RSLT_SUBTYPE, RSLT_VALUE, RSLT_TYPE, SHIP_DATE, DEVICE, SHP_SEQ_NBR) values ("
									+ "'"
									+ currentCustomer.getJob_id()
									+ "','"
									+ currentCustomer.getData_type()
									+ "','"
									+ currentCustomer.getTest_id()
									+ "','"
									+ currentCustomer.getCust_nbr()
									+ "','"
									+ currentCustomer.getInv_nbr()
									+ "','"
									+ currentCustomer.getTrkng_nbr()
									+ "','"
									+ columns.get(0).getText().trim()
									+ "','"
									+ columns.get(1).getText().trim()
									+ "','SHP','"
									+ currentCustomer.getShip_date()
									+ "','"
									+ currentCustomer.getDevice()
									+ "','"
									+ currentCustomer.getShp_seq_nbr() + "')";
							/*System.out
									.println("shpDetUpdate Info update sql  =>"
											+ shpDetUpdate);
*/
							insertQuery.add(shpDetUpdate);
						}
						
						
						if (driver.findElement(By.tagName("body")).getText()
								.contains("Tendered date") && rnum != 1) {

							/*System.out.println("Data Column1=>"
									+ columns.get(0).getText().trim() + "-"
									+ columns.get(1).getText().trim()); // +"-"+columns.get(2).getText().trim());
*/
							shpDetUpdate = "insert into mma_work_table (JOB_ID, DATA_TYPE, TEST_ID, CUST_NBR, INV_NBR, TRKNG_NBR, RSLT_SUBTYPE, RSLT_VALUE, RSLT_TYPE, SHIP_DATE, DEVICE, SHP_SEQ_NBR) values ("
									+ "'"
									+ currentCustomer.getJob_id()
									+ "','"
									+ currentCustomer.getData_type()
									+ "','"
									+ currentCustomer.getTest_id()
									+ "','"
									+ currentCustomer.getCust_nbr()
									+ "','"
									+ currentCustomer.getInv_nbr()
									+ "','"
									+ currentCustomer.getTrkng_nbr()
									+ "','"
									+ columns.get(0).getText().trim()
									+ "','"
									+ columns.get(1).getText().trim()
									+ "','SHP','"
									+ currentCustomer.getShip_date()
									+ "','"
									+ currentCustomer.getDevice()
									+ "','"
									+ currentCustomer.getShp_seq_nbr() + "')";
							/*System.out
									.println("shpDetUpdate Info update sql  =>"
											+ shpDetUpdate);*/

							insertQuery.add(shpDetUpdate);
						}
						

					}

					if (i == 2) {

						shipUpdate1 = "insert into mma_work_table (JOB_ID, DATA_TYPE, TEST_ID, CUST_NBR, INV_NBR, TRKNG_NBR, RSLT_SUBTYPE, RSLT_VALUE, RSLT_TYPE, SHIP_DATE, DEVICE, SHP_SEQ_NBR) values ("
								+ "'"
								+ currentCustomer.getJob_id()
								+ "','"
								+ currentCustomer.getData_type()
								+ "','"
								+ currentCustomer.getTest_id()
								+ "','"
								+ currentCustomer.getCust_nbr()
								+ "','"
								+ currentCustomer.getInv_nbr()
								+ "','"
								+ currentCustomer.getTrkng_nbr()
								+ "','BILL-"
								+ (rnum + 1)
								+ "','"
								+ columns.get(0).getText().trim()
								+ ""
								+ columns.get(1).getText().trim()
								+ "','SHIP','"
								+ currentCustomer.getShip_date()
								+ "','"
								+ currentCustomer.getDevice()
								+ "','"
								+ currentCustomer.getShp_seq_nbr() + "')";

						/*System.out.println("ShipUpdate1 Info update sql  =>"
								+ shipUpdate1);*/
						insertQuery.add(shipUpdate1);

					}

					if (i == 5) {

						chargeUpdate = "insert into mma_work_table (JOB_ID, DATA_TYPE, TEST_ID, CUST_NBR, INV_NBR, TRKNG_NBR, RSLT_SUBTYPE, RSLT_VALUE, RSLT_TYPE, SHIP_DATE, DEVICE, SHP_SEQ_NBR) values ("
								+ "'"
								+ currentCustomer.getJob_id()
								+ "','"
								+ currentCustomer.getData_type()
								+ "','"
								+ currentCustomer.getTest_id()
								+ "','"
								+ currentCustomer.getCust_nbr()
								+ "','"
								+ currentCustomer.getInv_nbr()
								+ "','"
								+ currentCustomer.getTrkng_nbr()
								+ "','"
								+ columns.get(0).getText().trim()
								+ "','"
								+ columns.get(1).getText().trim()
								+ "','CHG','"
								+ currentCustomer.getShip_date()
								+ "','"
								+ currentCustomer.getDevice()
								+ "','"
								+ currentCustomer.getShp_seq_nbr() + "')";
						/*System.out.println("chargeUpdate Info update sql  =>"
								+ chargeUpdate);*/

						insertQuery.add(chargeUpdate);

					}

					if (i == 6) {

						if (rnum < 3) {

							if (columns.get(0).getText().trim().length() > 0
									&& columns.get(1).getText().trim().length() > 0) {

								String lineWord = "";

								if (rnum == 0) {
									lineWord = "POD-Delivery date";
								}
								if (rnum == 1) {
									lineWord = "POD-Service area code";
								}
								if (rnum == 2) {
									lineWord = "POD-Signed by";
								}

								deliveryUpdate = "insert into mma_work_table (JOB_ID, DATA_TYPE, TEST_ID, CUST_NBR, INV_NBR, TRKNG_NBR, RSLT_SUBTYPE, RSLT_VALUE, RSLT_TYPE, SHIP_DATE, DEVICE, SHP_SEQ_NBR) values ("
										+ "'"
										+ currentCustomer.getJob_id()
										+ "','"
										+ currentCustomer.getData_type()
										+ "','"
										+ currentCustomer.getTest_id()
										+ "','"
										+ currentCustomer.getCust_nbr()
										+ "','"
										+ currentCustomer.getInv_nbr()
										+ "','"
										+ currentCustomer.getTrkng_nbr()
										+ "','"
										+ lineWord
										+ "','"
										+ columns.get(1).getText().trim()
										+ "','SHIP','"
										+ currentCustomer.getShip_date()
										+ "','"
										+ currentCustomer.getDevice()
										+ "','"
										+ currentCustomer.getShp_seq_nbr()
										+ "')";
								/*System.out
										.println("deliveryUpdate Info update sql  =>"
												+ deliveryUpdate);*/

								insertQuery.add(deliveryUpdate);

							}
						}
					}

					if (i == 8) {

						if (rnum < 4) {

							String col_Val = "";
							if (columns.get(1).getText().trim().length() > 0) {
								col_Val = columns.get(1).getText().trim().replace("'", "");
							} else {
								col_Val = "null";
							}

							String lineWord = "";

							if (rnum == 0) {
								lineWord = "OREF-Customer reference no.";
							}
							if (rnum == 1) {
								lineWord = "OREF-Department no.";
							}
							if (rnum == 2) {
								lineWord = "OREF-Reference #2";
							}
							if (rnum == 3) {
								lineWord = "OREF-Reference #3";
							}

							oRefUpdate = "insert into mma_work_table (JOB_ID, DATA_TYPE, TEST_ID, CUST_NBR, INV_NBR, TRKNG_NBR, RSLT_SUBTYPE, RSLT_VALUE, RSLT_TYPE, SHIP_DATE, DEVICE, SHP_SEQ_NBR) values ("
									+ "'"
									+ currentCustomer.getJob_id()
									+ "','"
									+ currentCustomer.getData_type()
									+ "','"
									+ currentCustomer.getTest_id()
									+ "','"
									+ currentCustomer.getCust_nbr()
									+ "','"
									+ currentCustomer.getInv_nbr()
									+ "','"
									+ currentCustomer.getTrkng_nbr()
									+ "','"
									+ lineWord
									+ "','"
									+ col_Val
									+ "','SHIP','"
									+ currentCustomer.getShip_date()
									+ "','"
									+ currentCustomer.getDevice()
									+ "','"
									+ currentCustomer.getShp_seq_nbr() + "')";
							/*System.out.println("oRefUpdate Info update sql  =>"
									+ oRefUpdate);*/

							insertQuery.add(oRefUpdate);

						}

					}

					if (i == 9) {

						if (rnum < 4) {
							String col_Val = "";
							if (columns.get(1).getText().trim().length() > 0) {
								col_Val = columns.get(1).getText().trim();
							} else {
								col_Val = "null";
							}

							String lineWord = "";

							if (rnum == 0) {
								lineWord = "UREF-Customer reference no.";
							}
							if (rnum == 1) {
								lineWord = "UREF-Department no.";
							}
							if (rnum == 2) {
								lineWord = "UREF-Reference #2";
							}
							if (rnum == 3) {
								lineWord = "UREF-Reference #3";
							}

							uRefUpdate = "insert into mma_work_table (JOB_ID, DATA_TYPE, TEST_ID, CUST_NBR, INV_NBR, TRKNG_NBR, RSLT_SUBTYPE, RSLT_VALUE, RSLT_TYPE, SHIP_DATE, DEVICE, SHP_SEQ_NBR) values ("
									+ "'"
									+ currentCustomer.getJob_id()
									+ "','"
									+ currentCustomer.getData_type()
									+ "','"
									+ currentCustomer.getTest_id()
									+ "','"
									+ currentCustomer.getCust_nbr()
									+ "','"
									+ currentCustomer.getInv_nbr()
									+ "','"
									+ currentCustomer.getTrkng_nbr()
									+ "','"
									+ lineWord
									+ "','"
									+ col_Val
									+ "','SHIP','"
									+ currentCustomer.getShip_date()
									+ "','"
									+ currentCustomer.getDevice()
									+ "','"
									+ currentCustomer.getShp_seq_nbr() + "')";
							/*System.out.println("uRefUpdate Info update sql  =>"
									+ uRefUpdate);*/

							insertQuery.add(uRefUpdate);

						}
					}

				}
			}
		}

		// Added for Messages

		int rowCount = driver
				.findElements(
						By.xpath("//span[text()='Messages']/../../../../tr[3]/td[2]//tr/td/div[1]/div"))
				.size();

		// System.out.println("-----------------------------------Rows in messages column is "+rowCount);

		int rnum1 = 0;
		if (rowCount > 0) {
			for (int k = 1; k <= rowCount; k++) {

				String varA = driver
						.findElement(
								By.xpath("//span[text()='Messages']/../../../../tr[3]/td[2]//tr/td/div[1]/div["
										+ k + "]//span")).getText();
				//System.out.println("The text found is " + varA);
				messageUpdate = "insert into mma_work_table (JOB_ID, DATA_TYPE, TEST_ID, CUST_NBR, INV_NBR, TRKNG_NBR, RSLT_SUBTYPE, RSLT_VALUE, RSLT_TYPE, SHIP_DATE, DEVICE, SHP_SEQ_NBR) values ("
						+ "'"
						+ currentCustomer.getJob_id()
						+ "','"
						+ currentCustomer.getData_type()
						+ "','"
						+ currentCustomer.getTest_id()
						+ "','"
						+ currentCustomer.getCust_nbr()
						+ "','"
						+ currentCustomer.getInv_nbr()
						+ "','"
						+ currentCustomer.getTrkng_nbr()
						+ "','MSG"
						+ (rnum1 + 1)
						+ "','"
						+ varA
						+ "','SHIP','"
						+ currentCustomer.getShip_date()
						+ "','"
						+ currentCustomer.getDevice()
						+ "','"
						+ currentCustomer.getShp_seq_nbr() + "')";
				//System.out.println("Message Update query is " + messageUpdate);
				insertQuery.add(messageUpdate);
				rnum1 = rnum1 + 1;

			}
		}

		return insertQuery;

	}

}
