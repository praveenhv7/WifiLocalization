package edu.neu.wireless.mapper;

import java.io.Serializable;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.List;

import edu.neu.wireless.bean.DistanceLocation;
import edu.neu.wireless.bean.MLDataPredictors;

public class TrainingDataMapper extends MLDataPredictors implements SQLData,Serializable {
	
	public String sql_type = "TRAINING_DATA";
	
	@Override
	public String getSQLTypeName() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		// TODO Auto-generated method stub
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
