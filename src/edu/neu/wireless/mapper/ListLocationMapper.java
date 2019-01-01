package edu.neu.wireless.mapper;

import java.io.Serializable;
import java.sql.Array;
import java.sql.SQLData;
import java.sql.SQLException;
import java.sql.SQLInput;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class ListLocationMapper implements SQLData,Serializable{
	
	private List<LocationMapper> listLocMap;
	private Array sqlArray; 
	private String sql_type;
	
	@Override
	public String getSQLTypeName() throws SQLException {
		// TODO Auto-generated method stub
		return "PRAVEEN.MATCHED_LOCATIONS_TAB";
		
	}
	@Override
	public void readSQL(SQLInput stream, String typeName) throws SQLException {
		// TODO Auto-generated method stub
		System.out.println("Entering");
		sql_type=typeName;
		setSqlArray(stream.readArray());
	}
	@Override
	public void writeSQL(SQLOutput stream) throws SQLException {
		// TODO Auto-generated method stub
		stream.writeArray(getSqlArray());
	}
	public List<LocationMapper> getListLocMap() {
		return listLocMap;
	}
	public void setListLocMap(List<LocationMapper> listLocMap) {
		this.listLocMap = listLocMap;
	}
	public Array getSqlArray() {
		return sqlArray;
	}
	public void setSqlArray(Array sqlArray) {
		this.sqlArray = sqlArray;
	}
	public String getSql_type() {
		return sql_type;
	}
	public void setSql_type(String sql_type) {
		this.sql_type = sql_type;
	}
	
	
	
	

}
