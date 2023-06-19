package testedesoftware.clinicacovid.unitTests.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import testedesoftware.clinicacovid.controller.AppointmentController;
import testedesoftware.clinicacovid.dao.AppointmentDao;
import testedesoftware.clinicacovid.model.Appointment;
import testedesoftware.clinicacovid.model.Calendar;
import testedesoftware.clinicacovid.model.CovidTestAppointment;
import testedesoftware.clinicacovid.model.DoctorAppointment;
import testedesoftware.clinicacovid.model.Patient;
import testedesoftware.clinicacovid.util.DateUtils;

public class AppointmentControllerTest {

	AppointmentController controller;
	AppointmentDao dao;
	
	@Before()
	public void setUp() {
		dao = mock(AppointmentDao.class);
		controller = new AppointmentController(dao);
	}
	
	@Test()
	public void getDoctorAppointmentsByPatient_empty() {		
		List<DoctorAppointment> list = controller.getDoctorAppointmentsByPatient(1);		
		assertTrue(list.isEmpty());
	}
	
	@Test()
	public void getDoctorAppointmentsByPatient_filled() {
		DoctorAppointment ap = new DoctorAppointment();
		when(dao.getDoctorAppointmentsByPatient(1)).thenReturn(Arrays.asList(ap));
		
		List<DoctorAppointment> list = controller.getDoctorAppointmentsByPatient(1);
		
		assertEquals(list.size(), 1);
		assertEquals(list.get(0), ap);		
	}
	
	@Test()
	public void getCovidTestAppointmentsByPatient_empty() {		
		List<CovidTestAppointment> list = controller.getCovidTestAppointmentsByPatient(1);		
		assertTrue(list.isEmpty());
	}
	
	@Test()
	public void getCovidTestAppointmentsByPatient_filled() {
		CovidTestAppointment ap = new CovidTestAppointment();
		when(dao.getCovidTestAppointmentsByPatient(1)).thenReturn(Arrays.asList(ap));
		
		List<CovidTestAppointment> list = controller.getCovidTestAppointmentsByPatient(1);
		
		assertEquals(list.size(), 1);
		assertEquals(list.get(0), ap);		
	}
	
	
	@Test()
	public void getAvailableTimes_noAppointments() throws ParseException {		
		Date date = DateUtils.stringToDate("06/06/2025 14:30", "dd/MM/yyyy HH:mm");
		List<Calendar> calendars = new ArrayList<>();
		
		List<Date> availableTimes = controller.getAvailableTimes(date, calendars);
		
		assertEquals(availableTimes.size(), 10);
		assertEquals(availableTimes.get(0), DateUtils.stringToDate("06/06/2025 15:00", "dd/MM/yyyy HH:mm"));
		assertEquals(availableTimes.get(9), DateUtils.stringToDate("07/06/2025 14:00", "dd/MM/yyyy HH:mm"));
	}
	
	@Test()
	public void getAvailableTimes_withAppointmentsAtSameCalendar() throws ParseException {		
		Date date = DateUtils.stringToDate("06/06/2025 14:30", "dd/MM/yyyy HH:mm");
		Appointment a1 = new Appointment(DateUtils.stringToDate("06/06/2025 15:00", "dd/MM/yyyy HH:mm"), new Patient());
		Appointment a2 = new Appointment(DateUtils.stringToDate("07/06/2025 08:00", "dd/MM/yyyy HH:mm"), new Patient());
		Calendar c1 = new Calendar(Arrays.asList(a1, a2));		
		List<Calendar> calendars = Arrays.asList(c1);
		
		List<Date> availableTimes = controller.getAvailableTimes(date, calendars);
		
		assertEquals(availableTimes.size(), 10);
		assertEquals(availableTimes.get(0), DateUtils.stringToDate("06/06/2025 16:00", "dd/MM/yyyy HH:mm"));
		assertEquals(availableTimes.get(9), DateUtils.stringToDate("07/06/2025 16:00", "dd/MM/yyyy HH:mm"));
	}
	
	@Test()
	public void getAvailableTimes_withAppointmentsAtDifferentCalendars() throws ParseException {		
		Date date = DateUtils.stringToDate("06/06/2025 14:30", "dd/MM/yyyy HH:mm");
		Appointment a1 = new Appointment(DateUtils.stringToDate("06/06/2025 15:00", "dd/MM/yyyy HH:mm"), new Patient());
		Appointment a2 = new Appointment(DateUtils.stringToDate("07/06/2025 08:00", "dd/MM/yyyy HH:mm"), new Patient());
		Calendar c1 = new Calendar(Arrays.asList(a1));
		Calendar c2 = new Calendar(Arrays.asList(a2));
		List<Calendar> calendars = Arrays.asList(c1, c2);
		
		List<Date> availableTimes = controller.getAvailableTimes(date, calendars);
		
		assertEquals(availableTimes.size(), 10);
		assertEquals(availableTimes.get(0), DateUtils.stringToDate("06/06/2025 15:00", "dd/MM/yyyy HH:mm"));
		assertEquals(availableTimes.get(9), DateUtils.stringToDate("07/06/2025 14:00", "dd/MM/yyyy HH:mm"));
	}
	
	@Test
	public void testSaveAppointment() {
		Appointment ap = new Appointment();
		controller.createAppointment(ap);		
		verify(dao).saveOrUpdate(ap);		
	}
	
	@Test(expected=NullPointerException.class)
	public void testCalendarAvailableAtDate_null() {
		controller.getCalendarAvailableAtDate(null, new Date());		
	}
	
	@Test
	public void testCalendarAvailableAtDate_empty() {
		List<Calendar> calendars = new ArrayList<>();
		Calendar cal = controller.getCalendarAvailableAtDate(calendars, new Date());
		assertNull(cal);
	}
	
	@Test
	public void testCalendarAvailableAtDate_freeCalendars() {
		Calendar c1 = new Calendar();
		Calendar c2 = new Calendar();
		List<Calendar> calendars = Arrays.asList(c1, c2);
		Calendar cal = controller.getCalendarAvailableAtDate(calendars, new Date());
		assertEquals(cal, c1);
	}
	
	@Test
	public void testCalendarAvailableAtDate_busyCalendars() {
		Date date = new Date();
		Appointment a1 = new Appointment(date, new Patient());
		Calendar c1 = new Calendar(Arrays.asList(a1));
		Calendar c2 = new Calendar();
		List<Calendar> calendars = Arrays.asList(c1, c2);
		Calendar cal = controller.getCalendarAvailableAtDate(calendars, date);
		assertEquals(cal, c2);
	}
}
