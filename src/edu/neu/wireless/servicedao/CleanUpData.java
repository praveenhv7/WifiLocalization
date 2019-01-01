package edu.neu.wireless.servicedao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

import oracle.jdbc.OracleCallableStatement;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;

public class CleanUpData implements ConnectionDataConst{
	

	public int cleanUpAllData() {
		
	Connection con = null;
		try {
			con=DriverManager.getConnection(URL, userName,password);
			System.out.println("Calling The procedure to clean UP");
			OracleCallableStatement ocs = 
					  (OracleCallableStatement)con.prepareCall("{ call REQ_DATA_CLEANUP }");
			ocs.execute();
			System.out.println("END OF KNN");
			return 1;
		}
		catch(Exception e) {
			e.printStackTrace();
			return 0;
		}
		finally {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
			
	}

}
