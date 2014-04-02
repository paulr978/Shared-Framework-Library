package my.pr.utils;

import java.util.*;
import java.io.Serializable;
import java.sql.*;
import java.text.SimpleDateFormat;

public class OTime implements Serializable {
	

	private static final long serialVersionUID = 7897310829942056895L;
	
	private Timestamp timestamp = null;
	
	public OTime(long time) {
		this.timestamp = new Timestamp(time);
	}
	
	public OTime(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	public OTime(String date) {
		this.timestamp = Timestamp.valueOf(date);
		
	}
	
	public OTime() {
		this.timestamp = new Timestamp(System.currentTimeMillis());
	}
	
	public Timestamp getTimestamp() {
		return timestamp;
	}
	
	public boolean isAfterNow() {
		return timestamp.after(new Timestamp(System.currentTimeMillis()));
	}
	
	public boolean isAfter(OTime oDate) {
		Timestamp t = oDate.timestamp;
		return timestamp.after(t);
	}
	
	public boolean isBeforeNow() {
		return timestamp.before(new Timestamp(System.currentTimeMillis()));
	}
	
	public boolean isBefore(OTime oDate) {
		Timestamp t = oDate.timestamp;
		return timestamp.before(t);
	}
	
	public long getLong() {
		return timestamp.getTime();
	}
	
	public OTime addMinutes(int minutes) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getDate());
		calendar.add(Calendar.MINUTE, minutes);
		return new OTime(calendar.getTimeInMillis());
	}
	
	public OTime addHours(int hours) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getDate());
		calendar.add(Calendar.HOUR, hours);
		return new OTime(calendar.getTimeInMillis());
	}
	
	public OTime addDays(int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getDate());
		calendar.add(Calendar.DATE, days);
		return new OTime(calendar.getTimeInMillis());
	}
	
	public String getFormattedDate(String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        java.util.Date date = new java.util.Date(timestamp.getTime());
        return dateFormat.format(date);
	}
	
	public String toString() {
		return getFormattedDate("yyyy-MM-dd HH:mm:ss");
	}
	
	private java.util.Date getDate() {
		return new java.util.Date(timestamp.getTime());
	}
	
	private java.util.Date getCurrentDate() {
		return new java.util.Date();
	}

}
