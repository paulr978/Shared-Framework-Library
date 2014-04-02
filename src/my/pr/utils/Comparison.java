package my.pr.utils;

public class Comparison {
	
	private Object obj1 = null;
	
	public Comparison(Object obj1) {
		this.obj1 = obj1;
	}
	
	public boolean isContains(String obj2) {
		return ((String)obj1).contains(obj2);
	}
	
	public boolean isEqualTo(String obj2) {
		return ((String)obj1).equalsIgnoreCase(obj2);
	}
	
	public boolean isNotEqualTo(String obj2) {
		return !((String)obj1).equalsIgnoreCase(obj2);
	}
	
	public boolean isGreaterThan(int obj2) {
		return ((Integer)obj1) > obj2;
	}
	
	public boolean isGreaterAndEqualThan(int obj2) {
		return ((Integer)obj1) >= obj2;
	}
	
	public boolean isLessThan(int obj2) {
		return ((Integer)obj1) < obj2;
	}
	
	public boolean isLessAndEqualThan(int obj2) {
		return ((Integer)obj1) <= obj2;
	}
	
	public boolean isTrue() {
		
		if(obj1 instanceof java.lang.String) {
			if(((String)obj1).equalsIgnoreCase("true")) {
				return true;
			}
			else {
				return false;
			}
		}
		else if(obj1 instanceof java.lang.Boolean) {
			if((Boolean)obj1) {
				return true;
			}
			else {
				return false;
			}
		}
		
		return false;

	}
	
	public boolean isFalse() {
		if(!(Boolean)obj1) {
			return true;
		}
		else {
			return false;
		}

	}

}
