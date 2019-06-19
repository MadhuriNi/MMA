package com.fedex.clm.mma_capture.Config;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import com.fedex.clm.mma_capture.Model.MMA_Model;

public class DataBaseActivity {

	ArrayList<MMA_Model> mma_data = new ArrayList<MMA_Model>();

	public ArrayList<MMA_Model> databaseSelect(Connection con,
			Properties propConfig) {
		
		ResultSet resultSet = null;
		PreparedStatement statement = null;

		try {

			
			statement = con.prepareStatement(propConfig
					.getProperty("select_sql_AccountsAndTrkng"));
			
			statement.setString(1, propConfig.getProperty("DATA_TYPE"));
			//statement.setString(2, propConfig.getProperty("THREAD_NO"));
			System.out.println("Statement 1 = >" + statement.toString());

			resultSet = statement.executeQuery();
		//	System.out.println("first result set==>");

			while (resultSet.next()) {

				MMA_Model model = new MMA_Model();

				model.setCust_nbr(resultSet.getString(1));
				model.setTrkng_nbr(resultSet.getString(2));
				model.setRslt_subtype(resultSet.getString(3));
				model.setRslt_value(resultSet.getString(4));
				model.setRslt_type(resultSet.getString(5));	
				model.setInv_nbr(resultSet.getString(6));
				model.setJob_id(resultSet.getString(7));
				model.setData_type(resultSet.getString(8));
				model.setTest_id(resultSet.getString(9));
				model.setShip_date(resultSet.getString(10));
				model.setDevice(resultSet.getString(11));
				model.setShp_seq_nbr(resultSet.getString(12));
				

				// Extracting login id/pass for a customer

				PreparedStatement statement1 = con.prepareStatement(propConfig
						.getProperty("select_id_pass_from_account"));
				statement1.setString(1, resultSet.getString(1));

				//System.out.println("Statement 1 = >" + statement1.toString());
				ResultSet resultSet1 = statement1.executeQuery();
				while (resultSet1.next()) {
					model.setAccount_login_id(resultSet1.getString(1));
					model.setAccount_login_password(resultSet1.getString(2));

				}
				resultSet1.close();
				statement1.close();
				mma_data.add(model);

			}
			
			
			
		} catch (Exception e) {
			System.out
					.println("Exception coming from pulling customer and tracking info from database==>"
							+ e);
		}
		
		finally {
	        try { resultSet.close(); statement.close();} catch (Exception ignore) { }
	    }

		return mma_data;

	}

	public Boolean databaseInitialSetupCommit(Connection con,
			Properties propConfig) {

		try {

			PreparedStatement statement1 = con.prepareStatement(propConfig
					.getProperty("update_sql_setting_Null"));
			statement1.setString(1, propConfig.getProperty("DATA_TYPE"));
			int s1 = statement1.executeUpdate();
			con.commit();

			PreparedStatement statement2 = con.prepareStatement(propConfig
					.getProperty("update_sql_setting_ErrorMsg"));
			statement2.setString(1, propConfig.getProperty("DATA_TYPE"));
			int s2 = statement2.executeUpdate();
			con.commit();

			PreparedStatement statement3 = con.prepareStatement(propConfig
					.getProperty("update_sql_setting_ErrorMsg_Invnbr"));
			statement3.setString(1, propConfig.getProperty("DATA_TYPE"));
			int s3 = statement3.executeUpdate();
			con.commit();
			return true;

		} catch (Exception e) {

			System.out.print("Exception from Initial DB setup ==>" + e);

			return false;
		}

	}

}
