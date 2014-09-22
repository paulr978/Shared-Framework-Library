/*
 * Program : Shared Framework Library
 * Author: Paul Rando (paulr978@gmail.com)
 * GIT: https://github.com/paulr978/Shared-Framework-Library
 * 
 */
package my.pr.utils;

public class OTableObject_bak {
	
	private long updated = -1L;
	private long accessed = -1L;
	private Object identifier = null;
	private Object obj = null;
	private boolean tagged = false;
	
	public OTableObject_bak() {
		
	}
	
	public OTableObject_bak(Object _identifier, Object _obj) {
		identifier = _identifier;
		obj = _obj;
		updated = System.currentTimeMillis();
	}
	
	public void setData(Object _identifier, Object _obj) {
		identifier = _identifier;
		obj = _obj;
		updated = System.currentTimeMillis();
	}
	
	public void setData(Object _identifier, Object _obj, boolean _tagged) {
		identifier = _identifier;
		obj = _obj;
		updated = System.currentTimeMillis();
		tagged = _tagged;
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
	
	public Object getObject() {
		accessed = System.currentTimeMillis();
		return obj;
	}

}
