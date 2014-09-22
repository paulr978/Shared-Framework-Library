/*
 * Program : Shared Framework Library
 * Author: Paul Rando (paulr978@gmail.com)
 * GIT: https://github.com/paulr978/Shared-Framework-Library
 * 
 */
package my.pr.properties;

import my.pr.utils.OException;

public class PropertiesFileNotFoundException extends OException {
	
	public PropertiesFileNotFoundException(Throwable t) {
		super("Properties File Not Found!", t);

	}

}
