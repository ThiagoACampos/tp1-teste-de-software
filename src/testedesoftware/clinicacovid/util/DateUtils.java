package testedesoftware.clinicacovid.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
}