package my.pr.properties;

import my.pr.utils.OException;

public class PropertyKeyNotFoundException extends OException {
	
	public PropertyKeyNotFoundException(String key) {
		super("Property Key (" + key + ") Not Found!");

	}

}
