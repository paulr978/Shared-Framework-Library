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
