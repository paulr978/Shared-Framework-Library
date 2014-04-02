package my.pr.utils;

import java.util.*;

public class OTable_bak {
	
	private Hashtable table = null;
	private long updated = -1L;
	
	public OTable_bak() {
		table = new Hashtable();
	}
	
	public synchronized void put(Object _key, Object _value) {
		
		if(table.containsKey(_key)) {
			OTableObject_bak tobj = (OTableObject_bak)table.get(_key);
			tobj.setData(_key, _value);
			table.put(_key, tobj);
		}
		else {
			OTableObject_bak tobj = new OTableObject_bak();
			tobj.setData(_key, _value);
			table.put(_key, tobj);
		}

		setUpdatedDTM();
	}
	
	public synchronized Object get(Object _key) {
		
		OTableObject_bak tobj = (OTableObject_bak)table.get(_key);
		return tobj.getObject();
	}
	
	public synchronized void addTag(Object _key) {
		((OTableObject_bak)table.get(_key)).addTag();
	}
	
	public synchronized void clearTag(Object _key) {
		((OTableObject_bak)table.get(_key)).clearTag();
	}
	
	public synchronized long getUpdatedDTM() {
		return updated;
	}
	
	public synchronized void clearTags() {
		
		Enumeration e = table.elements();
		
		while(e.hasMoreElements()) {
			
			OTableObject_bak tobj = (OTableObject_bak)e.nextElement();
			tobj.clearTag();
		}
		
	}
	
	public synchronized void fillTags() {
		
		Enumeration e = table.elements();
		
		while(e.hasMoreElements()) {
			
			OTableObject_bak tobj = (OTableObject_bak)e.nextElement();
			tobj.addTag();
		}
		
	}
	
	public synchronized void delete(Object _key) {
		table.remove(_key);
		setUpdatedDTM();
	}
	
	public synchronized void deleteTaggedElements() {
		
		Enumeration e = table.elements();
		
		while(e.hasMoreElements()) {
			
			OTableObject_bak tobj = (OTableObject_bak)e.nextElement();
			
			if(tobj.isTagged()) {
				table.remove(tobj.getIdentifier());
				deleteTableObject(tobj);
			}
		}
	}
	
	public synchronized void deleteUnTaggedElements() {
		
		Enumeration e = table.elements();
		
		while(e.hasMoreElements()) {
			
			OTableObject_bak tobj = (OTableObject_bak)e.nextElement();
			
			if(!tobj.isTagged()) {
				table.remove(tobj.getIdentifier());
				deleteTableObject(tobj);
			}
		}
	}
	
	public synchronized boolean isTagged(Object _key) {
		return ((OTableObject_bak)table.get(_key)).isTagged();
	}
	
	public synchronized int getSize() {
		return table.size();
	}
	
	public synchronized boolean containsKey(Object _key) {
		return table.containsKey(_key);
	}
	
	public synchronized int getUnTaggedSize() {
		
		int index = 0;
		
		Enumeration e = table.elements();
		
		while(e.hasMoreElements()) {
			
			OTableObject_bak tobj = (OTableObject_bak)e.nextElement();
			
			if(!tobj.isTagged()) {
				index++;
			}
		}
		
		return index;
	}
	
	public synchronized int getTaggedSize() {
		
		int index = 0;
		
		Enumeration e = table.elements();
		
		while(e.hasMoreElements()) {
			
			OTableObject_bak tobj = (OTableObject_bak)e.nextElement();
			
			if(tobj.isTagged()) {
				index++;
			}
		}
		
		return index;
	}
	
	public synchronized Object[] getUnTaggedElements() {
		
		Object[] values = new Object[getUnTaggedSize()];
		
		Enumeration e = table.elements();
		
		int index = 0;
		
		while(e.hasMoreElements()) {
			
			OTableObject_bak tobj = (OTableObject_bak)e.nextElement();

			if(!tobj.isTagged()) {
				values[index] = tobj.getObject();
			}
			
			index++;
		}
		
		return values;
	}
	
	
	public synchronized Object[] getTaggedElements() {
		
		Object[] values = new Object[getTaggedSize()];
		
		Enumeration e = table.elements();
		
		int index = 0;
		
		while(e.hasMoreElements()) {
			
			OTableObject_bak tobj = (OTableObject_bak)e.nextElement();

			if(tobj.isTagged()) {
				values[index] = tobj.getObject();
			}
			
			index++;
		}
		
		return values;
	}
	
	public synchronized Object[] getElements() {
		
		Object[] values = new Object[getSize()];
		
		Enumeration e = table.elements();
		
		int index = 0;
		
		while(e.hasMoreElements()) {
			
			OTableObject_bak tobj = (OTableObject_bak)e.nextElement();

			values[index] = tobj.getObject();
			
			index++;
		}
		
		return values;
	}
	
	public synchronized Object[] getKeys() {
		
		Object[] values = new Object[getSize()];
		
		Enumeration e = table.keys();
		
		int index = 0;
		
		while(e.hasMoreElements()) {
			
			Object key = e.nextElement();

			values[index] = key;
			
			index++;
		}
		
		return values;
	}
	
	private synchronized void setUpdatedDTM() {
		updated = System.currentTimeMillis();
	}
	
	private synchronized void deleteTableObject(OTableObject_bak _tobj) {
		Object identifier = _tobj.getIdentifier();
		Object obj = _tobj.getObject();
		identifier = null;
		obj = null;
		_tobj = null;
		setUpdatedDTM();
	}

}
