/*
 * Program : Shared Framework Library
 * Author: Paul Rando (paulr978@gmail.com)
 * GIT: https://github.com/paulr978/Shared-Framework-Library
 * 
 */
package my.pr.utils;

import java.util.*;

public class OTableRow {
	
	private long updated = -1L;
	private long accessed = -1L;
	private Object identifier = null;
	private Map rowData = null;
	private boolean tagged = false;
	
	public OTableRow(Object identifier) {
		this.identifier = identifier;
		rowData = new HashMap();
	}
	
	public Object getData(Object key) {
		accessed = System.currentTimeMillis();
		return rowData.get(key);
	}
	
	public void putData(Object key, Object value) {
		rowData.put(key, value);
		updated = System.currentTimeMillis();
	}
	
	public void putData(Object key, Object value, boolean _tagged) {
		rowData.put(key, value);
		updated = System.currentTimeMillis();
		tagged = _tagged;
	}
	
	public Map getMap() {
		return rowData;
	}
	
	public int getRowSize() {
		return rowData.size();
	}
	
	public void addTag() {
		tagged = true;
	}
	
	public void clearTag() {
		tagged = false;
	}
	
	public boolean isTagged() {
		return tagged;
	}
	
	public long getLastUpdatedDTM() {
		return updated;
	}
	
	public long getLastAccessedDTM() {
		return accessed;
	}
	
	public Object getIdentifier() {
		accessed = System.currentTimeMillis();
		return identifier;
	}
	
	public Map getRowData() {
		accessed = System.currentTimeMillis();
		return rowData;
	}

}
