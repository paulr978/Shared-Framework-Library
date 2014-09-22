/*
 * Program : Shared Framework Library
 * Author: Paul Rando (paulr978@gmail.com)
 * GIT: https://github.com/paulr978/Shared-Framework-Library
 * 
 */
package my.pr.properties;

import my.pr.utils.OResourceLocater;

import java.io.*;
import java.util.*;

public class OPropertiesLoader  {
	
	private String location = null;
	private OProperties props = null;
	
	
	public OPropertiesLoader(String location) throws PropertiesFileNotFoundException {
		this.location = location;
		
		props = refreshProperties();
		
	}
	
	public OProperties refreshProperties() throws PropertiesFileNotFoundException {
		OProperties refreshedProps = null;
		try {
			OResourceLocater locater = new OResourceLocater(location);
			refreshedProps = locater.getPropertiesFromResource();
		}
		catch(IOException e) {
			throw new PropertiesFileNotFoundException(e);
		}
		
		return refreshedProps;
	}
	
	public OProperties getProperties() {
		return props;
	}
	
	

}
