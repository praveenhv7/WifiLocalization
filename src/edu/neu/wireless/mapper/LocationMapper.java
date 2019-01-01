package edu.neu.wireless.mapper;

import java.io.Serializable;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;

import edu.neu.wireless.dto.LocationInfo;

public class LocationMapper extends LocationInfo implements SQLData,Serializable {
	
	
	public String sql_type = "MATCHED_LOCATIONS";
	@Override
	public String getSQLTypeName() throws SQLException {
		
		return "MATCHED_LOCATIONS";
	}

	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		
		this.sql_type = typeName;
		setLocation(stream.readString());
		setNumberOfReportedRecords(stream.readInt());
		
	}

	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		
		stream.writeString(getLocation());
		stream.writeInt(getNumberOfReportedRecords());
	}

	
	public String getSql_type() {
		return sql_type;
	}

	public void setSql_type(String sql_type) {
		this.sql_type = sql_type;
	}
	
	

}
