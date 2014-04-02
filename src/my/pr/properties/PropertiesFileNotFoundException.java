package my.pr.properties;

import my.pr.utils.OException;

public class PropertiesFileNotFoundException extends OException {
	
	public PropertiesFileNotFoundException(Throwable t) {
		super("Properties File Not Found!", t);

	}

}
