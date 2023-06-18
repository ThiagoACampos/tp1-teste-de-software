package testedesoftware.clinicacovid.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	private final static String DEFAULT_FORMAT = "dd/MM/yyyy";

	public static Date stringToDate(String strDate) throws ParseException {		
		SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_FORMAT);
		dateFormat.setLenient(false);
		return dateFormat.parse(strDate);		
	}
	
	public static Date stringToDate(String strDate, String format) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setLenient(false);
		return dateFormat.parse(strDate);
	}
	
	public static String dateToString(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_FORMAT);
		return dateFormat.format(date);
	}
	
	public static String dateToString(Date date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}
	
	public static Boolean isAtBusinessHours(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.HOUR_OF_DAY) >= 8 && cal.get(Calendar.HOUR_OF_DAY) < 18; 
	}
	
	public static Date getNextHour(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, 1);
		return cal.getTime();
	}
	
	public static Date getNextBusinessHoursStart(Date date) {
		
		if (DateUtils.isAtBusinessHours(date)) {
			return date;
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		if (cal.get(Calendar.HOUR) < 8) {
			cal.set(Calendar.HOUR_OF_DAY, 8);
		} else {
			cal.add(Calendar.DAY_OF_YEAR, 1);
			cal.set(Calendar.HOUR_OF_DAY, 8);
		}
		
		return cal.getTime(); 
	}
}
