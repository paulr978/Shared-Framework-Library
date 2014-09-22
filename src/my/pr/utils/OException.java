/*
 * Program : Shared Framework Library
 * Author: Paul Rando (paulr978@gmail.com)
 * GIT: https://github.com/paulr978/Shared-Framework-Library
 * 
 */
package my.pr.utils;

public class OException extends RuntimeException {
	
	//private Throwable throwable = null;
	
	public OException(String detail, Throwable throwable) {
		super(detail, throwable);
		//this.throwable = throwable;

	}
	
	public OException(String detail) {
		super(detail);

	}

}
