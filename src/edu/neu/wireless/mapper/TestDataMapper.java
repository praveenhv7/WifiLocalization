package edu.neu.wireless.mapper;

import java.io.Serializable;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

import edu.neu.wireless.bean.MLDataPredictors;

public class TestDataMapper extends MLDataPredictors implements SQLData,Serializable {
	
			
		public String sql_type = "TEST_DATA";
		
		@Override
		public String getSQLTypeName() throws SQLException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void readSQL(SQLInput stream, String typeName) throws SQLException {
			// TODO Auto-generated method stub
			/**
			 *  BSSID NUMBER(3),
			    SIGNAL_STRENGTH NUMBER(4),
			    AVG_SIGNAL_STRENGTH NUMBER(4),
			    RSS_SD NUMBER(4),
			    RSS_MIN_ALLOWED NUMBER(4),
			    RSS_MAX_ALLOWED NUMBER(4),
			    RSS_MAX NUMBER(4),
			    RSS_MIN NUMBER(4),
			    LOCATION VARCHAR2(200)
			 */
			
			this.sql_type = typeName;
			setBSSID(stream.readInt());
			setSignalStrength(stream.readDouble());
			setAvgSignalStrength(stream.readDouble());
			setRssSD(stream.readDouble());
			setRssMinAllowed(stream.readDouble());
			setRssMaxAllowed(stream.readDouble());
			setRssMax(stream.readDouble());
			setRssMin(stream.readDouble());
			setLocation(stream.readString());
			
		}

		@Override
		public void writeSQL(SQLOutput stream) throws SQLException {
			// TODO Auto-generated method stub
			
		}

		
}
