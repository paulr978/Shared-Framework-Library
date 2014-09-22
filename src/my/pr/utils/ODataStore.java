/*
 * Program : Shared Framework Library
 * Author: Paul Rando (paulr978@gmail.com)
 * GIT: https://github.com/paulr978/Shared-Framework-Library
 * 
 */
package my.pr.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class ODataStore implements Serializable {

	private Object data = null;
	
	private HashMap<String, String> values = null;
	private HashMap<String, String> dataTypes = null;
	
	private ODate lastUpdated = null;

	private static final long serialVersionUID = -9004310582960227517L;
	
	
	public ODataStore() {
		setObject(new Object());
		
		values = new HashMap<String, String>();
		dataTypes = new HashMap<String, String>();
	}
	
	public ODataStore(Object _data) {
		setObject(_data);
		
		values = new HashMap<String, String>();
		dataTypes = new HashMap<String, String>();
	}
	
	public String getJavaType() {
		return data.getClass().getCanonicalName();
	}
	
	public Object getObject() {
		return data;
	}
	
	public ODate getLastUpdated() {
		return lastUpdated;
	}
	
	public void setObject(Object _data) {
		data = _data;
		lastUpdated = new ODate();
	}
	
	public void setValue(String key, String value, String dataType) {
		values.put(key, value);
		dataTypes.put(key, dataType);
	}
	
	public void setValue(String key, String value) {
		values.put(key, value);
		dataTypes.put(key, "com.webdb.dataobjects.types.TextObjectStore");
	}
	
	public String getValue(String key) {
		return values.get(key);
	}
	
	public String getDataType(String key) {
		return dataTypes.get(key);
	}
	
	public Set getKeys() {
		return values.keySet();
		
	}

}
