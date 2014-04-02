package my.pr.utils;

import java.util.*;
import java.util.Date;
import java.io.Serializable;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class ODate implements Serializable {
	
	private final static long SECOND_MILLIS = 1000;
	private final static long MINUTE_MILLIS = SECOND_MILLIS * 60;
	private final static long HOUR_MILLIS = MINUTE_MILLIS * 60;

	private static final long serialVersionUID = 7897310829942056895L;
	
	private Timestamp timestamp = null;
	private String format = null;
	private Date formattedDate = null;
	
	//public static final String DEFAULT_TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	public static final String DEFAULT_TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public ODate(long time) {
		this.timestamp = new Timestamp(time);
	}
	
	public ODate(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	
	public ODate(String date) {
		this.timestamp = Timestamp.valueOf(date);	
	}
	
	public ODate(String _format, String dateString) {
		this.format = _format;
		//System.out.println(format + " " + dateString);
		SimpleDateFormat dateFormat = new SimpleDateFormat(this.format);
		try {
			formattedDate = dateFormat.parse(dateString);			
		}
		catch(ParseException e) {
			e.printStackTrace();
		}

		
		//this.timestamp = Timestamp.valueOf(date);	
		this.timestamp = generateFormattedTimestamp(formattedDate);
	}
	
	public ODate() {
		this.timestamp = new Timestamp(System.currentTimeMillis());
	}
	
	public static int secondsDiff(ODate earlierDate, ODate laterDate) {
		if(earlierDate == null || laterDate == null) {
			return 0;
		}
		return (int)((laterDate.getTimestamp().getTime() / SECOND_MILLIS) - (earlierDate.getTimestamp().getTime() / SECOND_MILLIS));
	}
	
	public static int minutesDiff(ODate earlierDate, ODate laterDate) {
		if(earlierDate == null || laterDate == null) {
			return 0;
		}
		return (int)((laterDate.getTimestamp().getTime() / MINUTE_MILLIS) - (earlierDate.getTimestamp().getTime() / MINUTE_MILLIS));
	}
	
	public static int hoursDiff(ODate earlierDate, ODate laterDate) {
		if(earlierDate == null || laterDate == null) {
			return 0;
		}
		return (int)((laterDate.getTimestamp().getTime() / HOUR_MILLIS) - (earlierDate.getTimestamp().getTime() / HOUR_MILLIS));
	}
	
	public Timestamp getTimestamp() {
		return timestamp;
	}
	
	public boolean isAfterNow() {
		return timestamp.after(new Timestamp(System.currentTimeMillis()));
	}
	
	public boolean isAfter(ODate oDate) {
		Timestamp t = oDate.timestamp;
		return timestamp.after(t);
	}
        
        public boolean isEqualOrAfter(ODate oDate) {
            if(this.equals(oDate)) {
                return true;
            }
            return isAfter(oDate);
        }
        
        public boolean isEqualOrBefore(ODate oDate) {
            if(this.equals(oDate)) {
                return true;
            }
            return isBefore(oDate);
        }
        
        public boolean equals(Object obj) {
            if(obj instanceof ODate) {
                ODate d = (ODate)obj;
                if(d.getTimestamp().equals(timestamp)) {
                    return true;
                }
            }
            return false;
        }
	
	public boolean isBeforeNow() {
		return timestamp.before(new Timestamp(System.currentTimeMillis()));
	}
	
	public boolean isBefore(ODate oDate) {
		Timestamp t = oDate.timestamp;
		return timestamp.before(t);
	}
        
        public boolean isBetween(ODate start, ODate end) {
            Timestamp s = start.timestamp;
            Timestamp e = end.timestamp;
            if(timestamp.equals(s) || timestamp.after(s)) {
                if(timestamp.equals(e) || timestamp.before(e)) {
                    return true;
                }
            }
            
            return false;
        }
	
	public long getLong() {
		return timestamp.getTime();
	}
	
	public ODate addMinutes(int minutes) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getDate());
		calendar.add(Calendar.MINUTE, minutes);
		return new ODate(calendar.getTimeInMillis());
	}
	
	public ODate addHours(int hours) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getDate());
		calendar.add(Calendar.HOUR, hours);
		return new ODate(calendar.getTimeInMillis());
	}
	
	public ODate addDays(int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getDate());
		calendar.add(Calendar.DATE, days);
		return new ODate(calendar.getTimeInMillis());
	}
        
        
	
	public String getFormattedDate(String format) throws IllegalArgumentException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		
		java.util.Date date = null;
		
		if(timestamp == null) {
			date = formattedDate;
		}
		else {
			date = new java.util.Date(timestamp.getTime());
		}

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
	
	private Timestamp generateFormattedTimestamp(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_TIMESTAMP_FORMAT);

		return Timestamp.valueOf(dateFormat.format(date));		
	}
        
        public static ODate getEndOfTimeDate() {
            return new ODate("yyyy-MM-dd HH:mm:ss", "3000-01-01 23:59:59");
        }
        
        public static ODate getBeginningOfTimeDate() {
            return new ODate("yyyy-MM-dd HH:mm:ss", "1900-01-01 00:00:00");
        }
        
        public static String getMinsSecsString(long millis) {
            return String.format("%d min, %d sec", 
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) - 
                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));

        }

}
