package com.fedex.clm.mma_capture.Config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
	
	public static Connection getConnection(Properties prop) throws IOException, SQLException{
		
		Connection con = null;

				
		try {
            Class.forName("oracle.jdbc.OracleDriver");
            String url="jdbc:oracle:thin:@//"+prop.getProperty("db_hostname")+":"+prop.getProperty("port")+"/"+prop.getProperty("service_name");
                       
            con = DriverManager.getConnection(url, prop.getProperty("dbuser"), prop.getProperty("dbpassword"));
            
        } catch (ClassNotFoundException ex) {
            // log an exception. for example:
            System.out.println("Driver not found."); 
        }
		return con;
					
	}
	
}
