package test.testedesoftware.clinicacovid.unitTests;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import testedesoftware.clinicacovid.exceptions.InvalidDateForSchedulingException;
import testedesoftware.clinicacovid.exceptions.NonexistingAppointmentException;
import testedesoftware.clinicacovid.exceptions.UnavailableDateForSchedulingException;
import testedesoftware.clinicacovid.model.Appointment;
import testedesoftware.clinicacovid.model.Calendar;
import testedesoftware.clinicacovid.model.Patient;

public class CalendarTest {
	
	private Calendar calendar;
	
	@BeforeEach
	void setUp() {
		calendar = new Calendar();
	}
	
	@Test
	void testScheduleOneAppointment() throws InvalidDateForSchedulingException, UnavailableDateForSchedulingException {
		Patient patient = new Patient("Bernardo", 30, "bernardo@gmail.com", "319999999", "bernardo");
		Date date = Date.from(new Date().toInstant().plus(1, ChronoUnit.HOURS));
		Appointment appointment = new Appointment(date, patient);
		
		calendar.schedule(appointment);
		
		assertEquals(calendar.getAppointments().size(), 1);
		assertEquals(calendar.getAppointments().get(0), appointment);
	}
	
	@Test
	void testScheduleMoreThanOneAppointments() throws InvalidDateForSchedulingException, UnavailableDateForSchedulingException {
		Patient patient = new Patient("Bernardo", 30, "bernardo@gmail.com", "319999999", "bernardo");	
		Date date1 = Date.from(new Date().toInstant().plus(1, ChronoUnit.HOURS));
		Date date2 = Date.from(new Date().toInstant().plus(2, ChronoUnit.HOURS));
		Appointment appointment1 = new Appointment(date1, patient);
		Appointment appointment2 = new Appointment(date2, patient);		
		
		calendar.schedule(appointment1);
		calendar.schedule(appointment2);
		
		assertEquals(calendar.getAppointments().size(), 2);
		assertTrue(calendar.getAppointments().contains(appointment1));
		assertTrue(calendar.getAppointments().contains(appointment2));
	}
	
	@Test
	void testScheduleAtInvalidDate() {
		Patient patient = new Patient("Bernardo", 30, "bernardo@gmail.com", "319999999", "bernardo");
		Date date = Date.from(new Date().toInstant().minus(1, ChronoUnit.HOURS));
		
		Appointment appointment = new Appointment(date, patient);
		
		Assertions.assertThrows(InvalidDateForSchedulingException.class, () -> {
			calendar.schedule(appointment);
		});		
	}	
	
	@Test
	void testScheduleAtUnavailableDate() throws InvalidDateForSchedulingException, UnavailableDateForSchedulingException {		
		Patient patient = new Patient("Bernardo", 30, "bernardo@gmail.com", "319999999", "bernardo");
		Date date = Date.from(new Date().toInstant().plus(1, ChronoUnit.HOURS));
		Appointment appointment1 = new Appointment(date, patient);
		Appointment appointment2 = new Appointment(date, patient);		
		
		calendar.schedule(appointment1);
		
		Assertions.assertThrows(UnavailableDateForSchedulingException.class, () -> {
			calendar.schedule(appointment2);
		});
	}
	
	
	@Test
	void testCancelAppointment() throws Exception {
		Patient patient = new Patient("Bernardo", 30, "bernardo@gmail.com", "319999999", "bernardo");
		Date date = Date.from(new Date().toInstant().plus(1, ChronoUnit.HOURS));
		Appointment appointment = new Appointment(date, patient);
		
		calendar.schedule(appointment);
		calendar.cancel(appointment);
		
		assertTrue(calendar.getAppointments().isEmpty());		
	}
	
	@Test
	void testCancelNonexistingAppointment() {
		Patient patient = new Patient("Bernardo", 30, "bernardo@gmail.com", "319999999", "bernardo");
		Date date = Date.from(new Date().toInstant().plus(1, ChronoUnit.HOURS));
		Appointment appointment = new Appointment(date, patient);
		
		Assertions.assertThrows(NonexistingAppointmentException.class, () -> {
			calendar.cancel(appointment);
		});		
	}
	

	@Test
	void testFilterByDayEmpty() throws InvalidDateForSchedulingException, UnavailableDateForSchedulingException {
		Patient patient = new Patient("Bernardo", 30, "bernardo@gmail.com", "319999999", "bernardo");
		Date today = Date.from(new Date().toInstant().plus(1, ChronoUnit.HOURS));
		Date tomorrow = Date.from(new Date().toInstant().plus(1, ChronoUnit.DAYS));
		
		calendar.schedule(new Appointment(today, patient));
		
		assertFalse(calendar.filterDay(today).isEmpty());
		assertTrue(calendar.filterDay(tomorrow).isEmpty());
	}
	
	@Test
	void testFilterByDayAtDifferentDays() throws InvalidDateForSchedulingException, UnavailableDateForSchedulingException {
		Patient patient = new Patient("Bernardo", 30, "bernardo@gmail.com", "319999999", "bernardo");
		Date today = Date.from(new Date().toInstant().plus(1, ChronoUnit.HOURS));
		Date tomorrow = Date.from(new Date().toInstant().plus(1, ChronoUnit.DAYS));
		Appointment appointmentToday = new Appointment(today, patient);
		Appointment appointmentTomorrow = new Appointment(tomorrow, patient);
				
		calendar.schedule(appointmentToday);
		calendar.schedule(appointmentTomorrow);
		
		assertEquals(calendar.filterDay(today).size(), 1);
		assertTrue(calendar.filterDay(today).contains(appointmentToday));
		assertEquals(calendar.filterDay(tomorrow).size(), 1);
		assertTrue(calendar.filterDay(tomorrow).contains(appointmentTomorrow));
	}
	
	@Test
	void testFilterByDayWithDifferentHours() throws InvalidDateForSchedulingException, UnavailableDateForSchedulingException {
		Patient patient = new Patient("Bernardo", 30, "bernardo@gmail.com", "319999999", "bernardo");
		
		java.util.Calendar c = java.util.Calendar.getInstance();		
		c.setTime(new Date());
		c.add(java.util.Calendar.DAY_OF_MONTH, 1);
		
        c.set(java.util.Calendar.HOUR_OF_DAY, 10);        
        Date date1 = c.getTime();        
        
        c.set(java.util.Calendar.HOUR_OF_DAY, 11);
        Date date2 = c.getTime();

		calendar.schedule(new Appointment(date1, patient));
		calendar.schedule(new Appointment(date2, patient));
		
		assertEquals(calendar.filterDay(date1).size(), 2);
		assertEquals(calendar.filterDay(date2).size(), 2);
	}
	
	
	@Test
	void testBusyAtWithEmptyAppointments() {		
		assertTrue(calendar.getAppointments().isEmpty());
		assertFalse(calendar.busyAt(new Date()));		
	}
	
	@Test
	void testBusyAtAvailableDate() {	
	}
	
	@Test
	void testBusyAtUnavailableDate() {	
	}
	
	@Test
	void testFilterByPatient() {

	}
	
	@Test
	void testClearSchedule() {
		
	}
	
	@Test
	void testCancelAppointsAtDate() {
		
	}
	
	@Test 
	void testCancelAppointmentsByPatient() {
		
	}
	
}