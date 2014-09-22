/*
 * Program : Shared Framework Library
 * Author: Paul Rando (paulr978@gmail.com)
 * GIT: https://github.com/paulr978/Shared-Framework-Library
 * 
 */
package my.pr.properties;

import java.util.*;



public class OProperties {
	
	private java.util.Properties props = null;
	
	
	public OProperties(java.util.Properties properties) {
		this.props = properties;

	}
	
	public String getValue(String key) throws PropertyKeyNotFoundException {
		boolean propExists = props.containsKey(key);
		
		if(propExists) {
			String value = (String)props.getProperty(key);
			//System.out.println("Properties - getValue(\"" + key + "\"), return value: " + value);
			return value;
		}
		else {
			//System.out.println("Properties - getValue(\"" + key + "\"), return value: null");
			throw new PropertyKeyNotFoundException(key);
		}

	}
	
	public void setValue(String key, String value) {
		System.out.println("Properties - setValue(\"" + key + "\", \"" + value + "\")");
		
		props.setProperty(key, value);
	}
	
	public void merge(java.util.Properties fromProperties) {
		
		Enumeration keys = fromProperties.keys();
		while(keys.hasMoreElements()) {
			String key = (String)keys.nextElement();
			String value = fromProperties.getProperty(key);
			setValue(key, value);
		}
		
	}
	
	public java.util.Properties getNativeProperties() {
		return props;
	}

}
