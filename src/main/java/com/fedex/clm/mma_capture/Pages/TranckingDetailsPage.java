package com.fedex.clm.mma_capture.Pages;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.fedex.clm.mma_capture.Model.MMA_Model;

public class TranckingDetailsPage {

	WebDriver driverTranckingDetailsPage;

	public TranckingDetailsPage(WebDriver driver) {

		this.driverTranckingDetailsPage = driver;
	}

	@FindBy(xpath = "//span[text()='Billing Information']/../../../../tr[3]/td[1]//table[1]")
	WebElement billingInformation1;// billingInformation
	@FindBy(xpath = "//span[text()='Billing Information']/../../../../tr[3]/td[1]//table[2]")
	WebElement billingInformation2;// billingInformation
	@FindBy(xpath = "//span[text()='Sender Information']/../../../../tr[3]/td[1]/table")
	WebElement senderInformation;// senderInformation
	@FindBy(xpath = "//span[text()='Recipient Information']/../../../../tr[3]/td[2]/table")
	WebElement recipientInformation;// recipientInformation
	@FindBy(xpath = "//span[text()='Shipment Details']/../../../../tr[3]/td[1]/table")
	WebElement shipmentDetails;// shipmentDetails
	@FindBy(xpath = "//span[text()='Charges']/../../../../tr[3]/td[2]/table/tbody/tr[1]//table")
	WebElement charges;// charges
	@FindBy(xpath = "//span[text()='Original Reference']/../../../../tr[3]/td[1]/table")
	WebElement originalReference;// originalReference
	@FindBy(xpath = "//span[text()='Updated Reference']/../../../../tr[3]/td[2]/table")
	WebElement updatedReference;// updatedReference
	@FindBy(xpath = "//span[text()='Proof of Delivery']/../../../../tr[3]/td[1]/table")
	WebElement proofOfDelivery;// proofOfDelivery
	@FindBy(xpath = "//span[text()='Cost Allocation Reference']/../../../../tr[3]/td[2]/table")
	WebElement costAllocationReference;// costAllocationReference

	public List<String> CatptureSenderReceipTable(WebDriver driver,
			MMA_Model currentCustomer, Properties propConfig, Connection con) {

		List<String> insertQuery = new ArrayList<String>();
		
		PreparedStatement pStmt = null;

		String insertTableSQL="insert into mma_work_table (JOB_ID, DATA_TYPE, TEST_ID, CUST_NBR, INV_NBR, TRKNG_NBR, RSLT_SUBTYPE, RSLT_VALUE, RSLT_TYPE, SHIP_DATE, DEVICE, SHP_SEQ_NBR) values (?,?,?,?,?,?,?,?,?,?,?,?)";
				
		try {
			
			pStmt=con.prepareStatement(insertTableSQL);
			con.setAutoCommit(false);
		

		String updateSQL = "UPDATE mma_work_table SET RSLT_SUBTYPE ='STATUS' , RSLT_VALUE = 'PASS', RSLT_TYPE = 'GFBO' ,THREAD_RECORD_STATUS='C' WHERE CUST_NBR = '"
				+ currentCustomer.getCust_nbr()
				+ "' AND TRKNG_NBR = '"
				+ currentCustomer.getTrkng_nbr()
				+ "' and RSLT_SUBTYPE='null' and RSLT_VALUE='null' and RSLT_TYPE='null'";
		insertQuery.add(updateSQL);

		List<WebElement> rowsBillingInformation1 = billingInformation1
				.findElements(By.tagName("tr"));

		
		for (int rnum = 0; rnum < rowsBillingInformation1.size(); rnum++) {
			List<WebElement> columns = rowsBillingInformation1.get(rnum)
					.findElements(By.tagName("td"));

			String billingInfoQuery = "insert into mma_work_table (JOB_ID, DATA_TYPE, TEST_ID, CUST_NBR, INV_NBR, TRKNG_NBR, RSLT_SUBTYPE, RSLT_VALUE, RSLT_TYPE, SHIP_DATE, DEVICE, SHP_SEQ_NBR) values ("
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

			/*System.out.println("billingInfoQuery1 Info update sql  =>"
					+ billingInfoQuery);*/

			insertQuery.add(billingInfoQuery);

		}

		List<WebElement> rowsBillingInformation2 = billingInformation2
				.findElements(By.tagName("tr"));

		/*System.out.println("billing Info rowSize:"
				+ rowsBillingInformation2.size());*/
		for (int rnum = 1; rnum < rowsBillingInformation2.size(); rnum++) {
			List<WebElement> columns = rowsBillingInformation2.get(rnum)
					.findElements(By.tagName("td"));

			String billingInfoQuery = "insert into mma_work_table (JOB_ID, DATA_TYPE, TEST_ID, CUST_NBR, INV_NBR, TRKNG_NBR, RSLT_SUBTYPE, RSLT_VALUE, RSLT_TYPE, SHIP_DATE, DEVICE, SHP_SEQ_NBR) values ("
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

			/*System.out.println("billingInfoQuery2 Info update sql  =>"
					+ billingInfoQuery);*/

			insertQuery.add(billingInfoQuery);

		}

		List<WebElement> rowsSenderInformation = senderInformation
				.findElements(By.tagName("tr"));
		for (int rnum = 0; rnum < rowsSenderInformation.size(); rnum++) {
			List<WebElement> columns = rowsSenderInformation.get(rnum)
					.findElements(By.tagName("td"));

			String col_Val = "";
			if (columns.get(0).getText().trim().length() > 0) {
				col_Val = columns.get(0).getText().trim();
			} else {
				col_Val = "null";
			}

			String senderInformation = "insert into mma_work_table (JOB_ID, DATA_TYPE, TEST_ID, CUST_NBR, INV_NBR, TRKNG_NBR, RSLT_SUBTYPE, RSLT_VALUE, RSLT_TYPE, SHIP_DATE, DEVICE, SHP_SEQ_NBR) values ("
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

			/*System.out
					.println("Sender Info update sql  =>" + senderInformation);*/

			insertQuery.add(senderInformation);

		}

		List<WebElement> rowsRecipientInformation = recipientInformation
				.findElements(By.tagName("tr"));
		for (int rnum = 0; rnum < rowsRecipientInformation.size(); rnum++) {
			List<WebElement> columns = rowsRecipientInformation.get(rnum)
					.findElements(By.tagName("td"));

			String col_Val = "";
			if (columns.get(0).getText().trim().length() > 0) {
				col_Val = columns.get(0).getText().trim();
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

			String recipientInformation = "insert into mma_work_table (JOB_ID, DATA_TYPE, TEST_ID, CUST_NBR, INV_NBR, TRKNG_NBR, RSLT_SUBTYPE, RSLT_VALUE, RSLT_TYPE, SHIP_DATE, DEVICE, SHP_SEQ_NBR) values ("
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
					+ recipientInformation);*/

			insertQuery.add(recipientInformation);

		}

		List<WebElement> rowsShipmentDetails = shipmentDetails.findElements(By
				.tagName("tr"));
		for (int rnum = 0; rnum < rowsShipmentDetails.size(); rnum++) {
			List<WebElement> columns = rowsShipmentDetails.get(rnum)
					.findElements(By.tagName("td"));

			if (columns.size() == 2) {

				/*if (!driver.findElement(By.tagName("body")).getText()
						.contains("Tendered date")) {*/

					/*
					 * System.out.println("Data Column1=>" +
					 * columns.get(0).getText().trim() + "-" +
					 * columns.get(1).getText().trim()); //
					 * +"-"+columns.get(2).getText().trim());
					 */
					String shipmentDetails = "insert into mma_work_table (JOB_ID, DATA_TYPE, TEST_ID, CUST_NBR, INV_NBR, TRKNG_NBR, RSLT_SUBTYPE, RSLT_VALUE, RSLT_TYPE, SHIP_DATE, DEVICE, SHP_SEQ_NBR) values ("
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

					/*System.out.println("shipmentDetails1 Info update sql  =>"
							+ shipmentDetails);*/

					insertQuery.add(shipmentDetails);
				/*}*/

				/*if (driver.findElement(By.tagName("body")).getText()
						.contains("Tendered date")
						&& rnum != 1) {

					
					 * System.out.println("Data Column1=>" +
					 * columns.get(0).getText().trim() + "-" +
					 * columns.get(1).getText().trim()); //
					 * +"-"+columns.get(2).getText().trim());
					 
					String shipmentDetails = "insert into mma_work_table (JOB_ID, DATA_TYPE, TEST_ID, CUST_NBR, INV_NBR, TRKNG_NBR, RSLT_SUBTYPE, RSLT_VALUE, RSLT_TYPE, SHIP_DATE, DEVICE, SHP_SEQ_NBR) values ("
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

					System.out.println("shipmentDetails2 Info update sql  =>"
							+ shipmentDetails);

					insertQuery.add(shipmentDetails);
				}*/
			}

		}

		List<WebElement> rowsCharges = charges.findElements(By.tagName("tr"));
		/*System.out
				.println("No of row's in charge table :" + rowsCharges.size());*/
		for (int rnum = 0; rnum < rowsCharges.size(); rnum++) {
			List<WebElement> columns = rowsCharges.get(rnum).findElements(
					By.tagName("td"));
			/*System.out.println("No of column in row " + rnum + ":"
					+ columns.size());*/

			String chargeUpdate = "insert into mma_work_table (JOB_ID, DATA_TYPE, TEST_ID, CUST_NBR, INV_NBR, TRKNG_NBR, RSLT_SUBTYPE, RSLT_VALUE, RSLT_TYPE, SHIP_DATE, DEVICE, SHP_SEQ_NBR) values ("
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

		List<WebElement> rowsOriginalReference = originalReference
				.findElements(By.tagName("tr"));
		for (int rnum = 0; rnum < rowsOriginalReference.size(); rnum++) {
			List<WebElement> columns = rowsOriginalReference.get(rnum)
					.findElements(By.tagName("td"));

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

				String oRefUpdate = "insert into mma_work_table (JOB_ID, DATA_TYPE, TEST_ID, CUST_NBR, INV_NBR, TRKNG_NBR, RSLT_SUBTYPE, RSLT_VALUE, RSLT_TYPE, SHIP_DATE, DEVICE, SHP_SEQ_NBR) values ("
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

		List<WebElement> rowsUpdatedReference = updatedReference
				.findElements(By.tagName("tr"));
		for (int rnum = 0; rnum < rowsUpdatedReference.size(); rnum++) {
			List<WebElement> columns = rowsUpdatedReference.get(rnum)
					.findElements(By.tagName("td"));

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

				String uRefUpdate = "insert into mma_work_table (JOB_ID, DATA_TYPE, TEST_ID, CUST_NBR, INV_NBR, TRKNG_NBR, RSLT_SUBTYPE, RSLT_VALUE, RSLT_TYPE, SHIP_DATE, DEVICE, SHP_SEQ_NBR) values ("
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

		List<WebElement> rowsProofOfDelivery = proofOfDelivery.findElements(By
				.tagName("tr"));
		for (int rnum = 0; rnum < rowsProofOfDelivery.size(); rnum++) {
			List<WebElement> columns = rowsProofOfDelivery.get(rnum)
					.findElements(By.tagName("td"));

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

					String deliveryUpdate = "insert into mma_work_table (JOB_ID, DATA_TYPE, TEST_ID, CUST_NBR, INV_NBR, TRKNG_NBR, RSLT_SUBTYPE, RSLT_VALUE, RSLT_TYPE, SHIP_DATE, DEVICE, SHP_SEQ_NBR) values ("
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
							+ currentCustomer.getShp_seq_nbr() + "')";

					/*System.out.println("deliveryUpdate Info update sql  =>"
							+ deliveryUpdate);*/

					insertQuery.add(deliveryUpdate);

				}

			}
		}

		// Added for Messages

		int rowCount = driver
				.findElements(
						By.xpath("//span[text()='Messages']/../../../../tr[3]/td[2]//tr/td/div[1]/div"))
				.size();

		// System.out.println("-----------------------------------Rows in
		// messages column is "+rowCount);

		int rnum1 = 0;
		if (rowCount > 0) {
			for (int k = 1; k <= rowCount; k++) {

				String varA = driver
						.findElement(
								By.xpath("//span[text()='Messages']/../../../../tr[3]/td[2]//tr/td/div[1]/div["
										+ k + "]//span")).getText();
				// System.out.println("The text found is " + varA);
				String messageUpdate = "insert into mma_work_table (JOB_ID, DATA_TYPE, TEST_ID, CUST_NBR, INV_NBR, TRKNG_NBR, RSLT_SUBTYPE, RSLT_VALUE, RSLT_TYPE, SHIP_DATE, DEVICE, SHP_SEQ_NBR) values ("
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
				/*System.out.println("Message Update query is " + messageUpdate);*/
				insertQuery.add(messageUpdate);
				rnum1 = rnum1 + 1;

			}
		}
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return insertQuery;
	}

}
