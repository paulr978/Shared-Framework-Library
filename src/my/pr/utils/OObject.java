package my.pr.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.sql.Timestamp;
import java.util.*;
import java.io.Serializable;
import java.lang.reflect.*;


public class OObject implements Serializable {
	
	private static final long serialVersionUID = 3365667849476887438L;
	private int databaseIdentifier = -1;
	private int serialIdentifier = -1;
	private Vector<OObject> attributes = null;
	private Map testAttributes = null;
	//private Map<Integer, Attribute> attributes = null;
	//private Map<String, Integer> attributeNames = null;
	
	public OObject() {
		//attributes = new HashMap<Integer, Attribute>();
		//attributeNames = new HashMap<String, Integer>();
		attributes = new Vector<OObject>();
		testAttributes = new HashMap();
		//testAttributes.put("test1", 1);
		//testAttributes.put("test2", 2);
	}
	
	public OObject(int _databaseIdentifier) {
		this.databaseIdentifier = _databaseIdentifier;
		//attributes = new HashMap<Integer, Attribute>();
		//attributeNames = new HashMap<String, Integer>();
		attributes = new Vector<OObject>();
		testAttributes = new HashMap();
		//testAttributes.put("test1", 1);
		//testAttributes.put("test2", 2);
	}
	
	public void setSerialIdentifier(int serialIdentifier) {
		this.serialIdentifier = serialIdentifier;
	}
	
	public int getSerialIdentifier() {
		return serialIdentifier;
	}
	
	
	public void addAttribute(OObject attr) {
		attributes.add(attr);
	}
	
	public Iterator getAttributes() {
		return attributes.iterator();
	}
	
	public Map getTestAttributes() {
		return testAttributes;
	}
        
        public static InputStream getObjectInputStream(Object obj) throws Exception {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(obj);
            oos.flush();
            oos.close();
            return new ByteArrayInputStream(baos.toByteArray());
        }
	
	/*
	public void setAttribute(Attribute attr) {
		int index = attributes.size();
		attributes.put(index, attr);
		attributeNames.put(attr.getName(), index);
	}
	
	public Attribute getAttribute(String name) {
		return getAttribute(attributeNames.get(name));
	}
	
	public Attribute getAttribute(int index) {
		return attributes.get(index);
	}
	
	public AttributeObject getValue(String name) throws ProcessingException, ValidationException {
		return getValue(attributeNames.get(name));
	}
	
	public AttributeObject getValue(int index) throws ProcessingException, ValidationException {
		return getAttribute(index).getValue();
	}
	
	
	
	
	public int getDatabaseIdentifier() {
		return databaseIdentifier;
	}
	
	*/
	
	public void mergeWith(OObject o) {
		
    	Method[] methods = o.getClass().getMethods();
    	
    	for(int index1 = 0; index1 < methods.length; index1++) {
    		Method method = methods[index1];

    		if(!method.getReturnType().getName().equalsIgnoreCase("void")) {
    			
    			try {

    				//System.out.println(method.getName() + " " + StringProcessor.isGetterMethod(method.getName()));
    				if(StringProcessor.isGetterMethod(method.getName()) && !method.getName().equalsIgnoreCase("getClass") && !method.getName().equalsIgnoreCase("getSerialIdentifier")) {
    				//if(method.getName().startsWith("get") /*|| method.getName().startsWith("is") || method.getName().startsWith("has")*/) {

    					String setMethodName = StringProcessor.replaceGetterWithSetter(method.getName());
    					//System.out.println(method.getName() + " " + setMethodName);
    					//String setMethodName = method.getName().replaceFirst("get", "set");
    					
    					//System.out.println(setMethodName);
    					
    					//System.out.println(method.getName());
    					Object returnObj = method.invoke(o, null);
    					//System.out.println(returnObj.getClass().getCanonicalName());
    					
    					if(returnObj != null) {
    						if(returnObj.getClass().getCanonicalName().equalsIgnoreCase("boolean") || returnObj.getClass().getCanonicalName().equalsIgnoreCase("java.lang.Boolean")) {
            					//method.invoke(constructedObj, Boolean.parseBoolean(attribute.getNodeValue()));
            					//System.out.println((Boolean)returnObj);
            					Method setMethod = this.getClass().getMethod(setMethodName, Boolean.TYPE);
            					setMethod.invoke(this, returnObj);
            				}
            				
                			
            				if(returnObj.getClass().getCanonicalName().equalsIgnoreCase("int") || returnObj.getClass().getCanonicalName().equalsIgnoreCase("java.lang.Integer")) {
            					//method.invoke(constructedObj, Integer.parseInt(attribute.getNodeValue()));
            					//System.out.println((Integer)returnObj);
            					Method setMethod = this.getClass().getMethod(setMethodName, Integer.TYPE);
            					setMethod.invoke(this, returnObj);
            				}
                			
            				if(returnObj.getClass().getCanonicalName().equalsIgnoreCase("long") || returnObj.getClass().getCanonicalName().equalsIgnoreCase("java.lang.Long")) {
            					//method.invoke(constructedObj, Long.parseLong(attribute.getNodeValue()));
            					//System.out.println((Long)returnObj);
            					Method setMethod = this.getClass().getMethod(setMethodName, Long.TYPE);
            					setMethod.invoke(this, returnObj);
            				}
            				
            				if(returnObj.getClass().getCanonicalName().equalsIgnoreCase("java.lang.String")) {
            					//method.invoke(constructedObj, attribute.getNodeValue());
            					//System.out.println((String)returnObj);
            					Method setMethod = this.getClass().getMethod(setMethodName, String.class);
            					setMethod.invoke(this, returnObj);
            				}
            				
            				if(returnObj.getClass().getCanonicalName().equalsIgnoreCase("com.ownerstech.utils.ODate")) {
            					//method.invoke(constructedObj, attribute.getNodeValue());
            					//System.out.println((String)returnObj);
            					Method setMethod = this.getClass().getMethod(setMethodName, ODate.class);
            					setMethod.invoke(this, returnObj);
            				}
    						
    					}
        				
    					
    					//element.setAttribute(method.getName(), String.valueOf(returnObj));
    					//System.out.println(method.getName() + " " + String.valueOf(returnObj));
    					//Log.debug("API Bean Processed to XML Element -> " + method.getName() + " " + returnObj);
    					
    					
    				}
    				
    			}
    			catch(Exception e) {
    				e.printStackTrace();
    			}

    		}
    		
    	}
	}	
	

}
