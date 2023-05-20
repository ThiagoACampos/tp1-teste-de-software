package test.testedesoftware.clinicacovid.unitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import testedesoftware.clinicacovid.exceptions.InvalidDateForSchedulingException;
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
}