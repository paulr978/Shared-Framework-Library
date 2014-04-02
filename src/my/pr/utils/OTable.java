package my.pr.utils;

import java.util.*;

public class OTable {
	
	private Hashtable<Object, OTableRow> table = null;
	private long updated = -1L;
	
	public OTable() {
		table = new Hashtable<Object, OTableRow>();
	}
	
	public void put(Object _id, Object _key, Object _value) {
		
		if(table.containsKey(_id)) {
			OTableRow tobj = (OTableRow)table.get(_id);
			tobj.putData(_key, _value);
			table.put(_id, tobj);
		}
		else {
			OTableRow tobj = new OTableRow(_id);
			tobj.putData(_key, _value);
			table.put(_id, tobj);
		}

		setUpdatedDTM();
	}
	
	public int getIdentifierSize() {
		return table.size();
	}
	
	public int getRowSize(Object _id) {
		return table.get(_id).getRowSize();
	}
	
	public Object get(Object _id, Object _key) {
		
		OTableRow tobj = (OTableRow)table.get(_id);
		//System.out.println("OTR: " + tobj.getData(_key));
		return tobj.getData(_key);
	}
	
	public Map getKeyValueMap(Object _id) {
		Map map = new HashMap();
		OTableRow tobj = (OTableRow)table.get(_id);
		return tobj.getMap();
	}
	
	public void addTag(Object _key) {
		((OTableRow)table.get(_key)).addTag();
	}
	
	public void clearTag(Object _key) {
		((OTableRow)table.get(_key)).clearTag();
	}
	
	public long getUpdatedDTM() {
		return updated;
	}
	
	public void clearTags() {
		
		Enumeration e = table.elements();
		
		while(e.hasMoreElements()) {
			
			OTableRow tobj = (OTableRow)e.nextElement();
			tobj.clearTag();
		}
		
	}
	
	public void fillTags() {
		
		Enumeration e = table.elements();
		
		while(e.hasMoreElements()) {
			
			OTableRow tobj = (OTableRow)e.nextElement();
			tobj.addTag();
		}
		
	}
	
	public void delete(Object _key) {
		table.remove(_key);
		setUpdatedDTM();
	}
	/*
	public void deleteTaggedElements() {
		
		Enumeration e = table.elements();
		
		while(e.hasMoreElements()) {
			
			OTableRow tobj = (OTableRow)e.nextElement();
			
			if(tobj.isTagged()) {
				table.remove(tobj.getIdentifier());
				deleteTableObject(tobj);
			}
		}
	}*/
	/*
	public void deleteUnTaggedElements() {
		
		Enumeration e = table.elements();
		
		while(e.hasMoreElements()) {
			
			OTableRow tobj = (OTableRow)e.nextElement();
			
			if(!tobj.isTagged()) {
				table.remove(tobj.getIdentifier());
				deleteTableObject(tobj);
			}
		}
	}*/
	
	public boolean isTagged(Object _key) {
		return ((OTableRow)table.get(_key)).isTagged();
	}
	
	public int getSize() {
		return table.size();
	}
	
	public boolean containsKey(Object _key) {
		return table.containsKey(_key);
	}
	
	public int getUnTaggedSize() {
		
		int index = 0;
		
		Enumeration e = table.elements();
		
		while(e.hasMoreElements()) {
			
			OTableRow tobj = (OTableRow)e.nextElement();
			
			if(!tobj.isTagged()) {
				index++;
			}
		}
		
		return index;
	}
	
	public int getTaggedSize() {
		
		int index = 0;
		
		Enumeration e = table.elements();
		
		while(e.hasMoreElements()) {
			
			OTableRow tobj = (OTableRow)e.nextElement();
			
			if(tobj.isTagged()) {
				index++;
			}
		}
		
		return index;
	}
	/*
	public Object[] getUnTaggedElements() {
		
		Object[] values = new Object[getUnTaggedSize()];
		
		Enumeration e = table.elements();
		
		int index = 0;
		
		while(e.hasMoreElements()) {
			
			OTableRow tobj = (OTableRow)e.nextElement();

			if(!tobj.isTagged()) {
				values[index] = tobj.getObject();
			}
			
			index++;
		}
		
		return values;
	}*/
	
	/*
	public Object[] getTaggedElements() {
		
		Object[] values = new Object[getTaggedSize()];
		
		Enumeration e = table.elements();
		
		int index = 0;
		while(e.hasMoreElements()) {
			
			OTableRow tobj = (OTableRow)e.nextElement();

			if(tobj.isTagged()) {
				values[index] = tobj.getData();
			}
			
			index++;
		}
		
		return values;
	}*/
	
	/*
	public Object[] getElements() {
		
		Object[] values = new Object[getSize()];
		
		Enumeration e = table.elements();
		
		int index = 0;
		
		while(e.hasMoreElements()) {
			
			OTableRow tobj = (OTableRow)e.nextElement();

			values[index] = tobj.getObject();
			
			index++;
		}
		
		return values;
	}*/
	
	public Object[] getKeys() {
		
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
	
	private void setUpdatedDTM() {
		updated = System.currentTimeMillis();
	}
	
	/*
	private void deleteTableObject(OTableRow _tobj) {
		Object identifier = _tobj.getIdentifier();
		Object obj = _tobj.getObject();
		identifier = null;
		obj = null;
		_tobj = null;
		setUpdatedDTM();
	}*/

}
