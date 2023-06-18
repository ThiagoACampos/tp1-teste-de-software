package testedesoftware.clinicacovid.unitTests.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import testedesoftware.clinicacovid.util.DateUtils;

public class DateUtilsTest {

	@Test
	public void testStringToDateUsingDefaultFormat() throws ParseException {		
		Date date = DateUtils.stringToDate("06/05/2010");		
		Calendar cal = Calendar.getInstance();		
		cal.setTime(date);
		
		assertEquals(6, cal.get(Calendar.DATE));
		assertEquals(5, cal.get(Calendar.MONTH) + 1);
		assertEquals(2010, cal.get(Calendar.YEAR));
		assertEquals(0, cal.get(Calendar.HOUR));
	}
	
	@Test
	public void testStringToDateUsingCustomFormat() throws ParseException {		
		Date date = DateUtils.stringToDate("2010-05-06", "yyyy-MM-dd");		
		Calendar cal = Calendar.getInstance();		
		cal.setTime(date);
		
		assertEquals(6, cal.get(Calendar.DATE));
		assertEquals(5, cal.get(Calendar.MONTH) + 1);
		assertEquals(2010, cal.get(Calendar.YEAR));
		assertEquals(0, cal.get(Calendar.HOUR));
	}
	
	@Test(expected=ParseException.class)
	public void testStringToDateParseException_emptyString() throws ParseException {		
		DateUtils.stringToDate("");
	}
	
	@Test(expected=ParseException.class)
	public void testStringToDateParseException_invalidMonth() throws ParseException {		
		Date date = DateUtils.stringToDate("06/13/2010");
		System.out.println(date);
	}	
	
	@Test(expected=ParseException.class)
	public void testStringToDateParseException_invalidFormat() throws ParseException {		
		DateUtils.stringToDate("06/05/2010", "yyyy-MM-dd");
	}
	
	
	@Test
	public void testDateToStringUsingDefaultFormat() {			
		Calendar cal = Calendar.getInstance();		
		cal.set(2013, 7 - 1, 24);		
		
		String strDate = DateUtils.dateToString(cal.getTime());		
		
		assertEquals("24/07/2013", strDate);
	}
	
	@Test
	public void testDateToStringUsingCustomFormat() {			
		Calendar cal = Calendar.getInstance();		
		cal.set(2013, 7 - 1, 24);		
		
		String strDate = DateUtils.dateToString(cal.getTime(), "yyyy-MM-dd");		
		
		assertEquals("2013-07-24", strDate);
	}
	
	@Test
	public void testIsBusinessHoursWhenItsNot() throws ParseException {
		Date inferior = DateUtils.stringToDate("2030-01-01 07:59", "yy-MM-dd HH:mm");
		Date superior = DateUtils.stringToDate("2030-01-01 18:00", "yy-MM-dd HH:mm");
		
		assertFalse(DateUtils.isAtBusinessHours(inferior));
		assertFalse(DateUtils.isAtBusinessHours(superior));
	}
	
	@Test
	public void testIsBusinessHoursWhenItsIs() throws ParseException {
		Date inferior = DateUtils.stringToDate("2030-01-01 08:00", "yy-MM-dd HH:mm");
		Date superior = DateUtils.stringToDate("2030-01-01 17:59", "yy-MM-dd HH:mm");
		
		assertTrue(DateUtils.isAtBusinessHours(inferior));
		assertTrue(DateUtils.isAtBusinessHours(superior));
	}
	
	@Test
	public void testGetNextHour() throws ParseException {
		Date date = new Date();
		Date nextHour = DateUtils.getNextHour(date);		
		
		Long diff = TimeUnit.HOURS.convert(nextHour.getTime() - date.getTime(), TimeUnit.MILLISECONDS);		
		assertTrue(nextHour.after(date));
		assertEquals(diff, 1);
	}
	
	@Test
	public void testGetNextBusinessHoursStart_alreadyIsBusinessHours() throws ParseException {
		Date date = DateUtils.stringToDate("2030-01-01 12:00", "yy-MM-dd HH:mm");		
		Date nextStart = DateUtils.getNextBusinessHoursStart(date); 		
		assertEquals(date, nextStart);
	}
	
	@Test
	public void testGetNextBusinessHoursStart_beforeBusinessHours() throws ParseException {
		Date date = DateUtils.stringToDate("2030-01-01 06:00", "yy-MM-dd HH:mm");		
		Date nextStart = DateUtils.getNextBusinessHoursStart(date);
		
		assertEquals(DateUtils.stringToDate("2030-01-01 08:00", "yy-MM-dd HH:mm"), nextStart);
	}
	
	@Test
	public void testGetNextBusinessHoursStart_afterBusinessHours() throws ParseException {
		Date date = DateUtils.stringToDate("2030-01-01 20:00", "yy-MM-dd HH:mm");		
		Date nextStart = DateUtils.getNextBusinessHoursStart(date);
		
		assertEquals(DateUtils.stringToDate("2030-01-02 08:00", "yy-MM-dd HH:mm"), nextStart);
	}
	
}
