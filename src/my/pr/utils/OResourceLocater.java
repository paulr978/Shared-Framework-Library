package my.pr.utils;

import java.net.*;
import java.io.*;
import java.util.*;

import my.pr.properties.OProperties;

public class OResourceLocater {
	
	private String location = null;
	private boolean isFile = false;
	
	private File file = null;
	private byte[] bytes = null;
	
	private int bytesBuffer = 512;
	
	public OResourceLocater(String location) throws IOException {
		
		long before = System.currentTimeMillis();
		
		InputStream input = null;
		
		//Check if location is a File
		File checkFile = new File(location);
		isFile = checkFile.isFile();
		
		if(!isFile) {
			
			System.out.println("Resource Loading: " + location + " is not a valid file.");
			
			if(!location.contains("/")) {
				this.location = "/" + location;
			}
			
			else {
				this.location = location;
			}
			
			URL url = getClass().getResource(this.location);
			input = url.openStream();

		}
		
		else {
			
			System.out.println("Resource Loading: " + location + " is a valid file.");
			this.location = location;
			file = checkFile;
			input = new FileInputStream(file);

		}
		
		ByteArrayOutputStream output = new ByteArrayOutputStream();
		byte[] chunk = new byte[bytesBuffer];
		
		int readBytes;
		while ((readBytes = input.read(chunk)) > 0) {
			output.write(chunk, 0, readBytes);
		}
		
		bytes = output.toByteArray();
		System.out.println("Resource Loaded Successful: " + bytes.length + " total bytes read.");
		
		input.close();
		output.close();
		
		long after = System.currentTimeMillis();
		System.out.println("Resource Time to Load: " + (after - before) + " milliseconds.");
	}
	
	public byte[] getBytes() {
		return bytes;
	}
	
	public File createNewFileFromResource(String destination) throws FileNotFoundException, IOException {
		File newFile = new File(destination);
		
		if(newFile.exists()) {
			throw new IOException("File Already Exists...");
		}
		
		FileOutputStream output = new FileOutputStream(newFile);
		output.write(bytes);
		output.close();
		
		return newFile;
	}
	
	public File getFileFromResource() throws FileNotFoundException, IOException{
		if(file == null) {
			FileOutputStream output = new FileOutputStream(file);
			output.write(bytes);
			output.close();
		}
		
		return file;
	}
	
	public OProperties getSimplePropertiesFromResource() throws IOException {
		java.util.Properties properties = new java.util.Properties();
		ByteArrayInputStream input = new ByteArrayInputStream(bytes);
		properties.load(input);
		
		Enumeration e = properties.elements();
		
		Enumeration keys = properties.keys();
		
		while(keys.hasMoreElements()) {
			String key = (String)keys.nextElement();
			String value = properties.getProperty(key);
			
			properties.put(key, value);
		
			System.out.println("SIMPLE PROPERTY LOADED: Key: " + key + " Value: " + properties.getProperty(key));
		}
		
		
		
		input.close();
		
		return new OProperties(properties);
	}
	
	
	public OProperties getPropertiesFromResource() throws IOException {
		java.util.Properties properties = new java.util.Properties();
		ByteArrayInputStream input = new ByteArrayInputStream(bytes);
		properties.load(input);
		
		Enumeration e = properties.elements();
		
		Enumeration keys = properties.keys();
		
		while(keys.hasMoreElements()) {
			String key = (String)keys.nextElement();
			String value = properties.getProperty(key);
			
			while(value.contains("{") && value.contains("}")) {
				if(value.contains("{") && value.contains("}")) {
					String innerKey = value.substring(value.indexOf("{") + 1, value.indexOf("}"));
					String innerValue = properties.getProperty(innerKey);
					innerKey = "\\{" + innerKey + "\\}";

					value = value.replaceFirst(innerKey, innerValue);
					
				}
				
			}
			
			properties.put(key, value);
		
			System.out.println("PROPERTY LOADED: Key: " + key + " Value: " + properties.getProperty(key));
		}
		
		
		
		input.close();
		
		return new OProperties(properties);
	}
	

}
