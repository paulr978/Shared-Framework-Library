/*
 * Program : Shared Framework Library
 * Author: Paul Rando (paulr978@gmail.com)
 * GIT: https://github.com/paulr978/Shared-Framework-Library
 * 
 */
package my.pr.properties;

import my.pr.utils.OException;

public class PropertyKeyNotFoundException extends OException {
	
	public PropertyKeyNotFoundException(String key) {
		super("Property Key (" + key + ") Not Found!");

	}

}
