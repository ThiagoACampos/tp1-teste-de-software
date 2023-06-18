package testedesoftware.clinicacovid.unitTests.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import testedesoftware.clinicacovid.controller.DoctorController;
import testedesoftware.clinicacovid.dao.DoctorDao;
import testedesoftware.clinicacovid.enums.DoctorAction;
import testedesoftware.clinicacovid.enums.DoctorExpertise;
import testedesoftware.clinicacovid.model.Appointment;
import testedesoftware.clinicacovid.model.Calendar;
import testedesoftware.clinicacovid.model.Doctor;
import testedesoftware.clinicacovid.model.Patient;
import testedesoftware.clinicacovid.view.DoctorView;
import testedesoftware.clinicacovid.view.MenuOption;

public class DoctorControllerTest {
	
	
	DoctorController controller;
	DoctorDao dao;
	DoctorView view;	
	
	@Before
	public void setUp() {
		dao = mock(DoctorDao.class);
		view = mock(DoctorView.class);
		Doctor defaultDoctor = new Doctor("AB-123456", DoctorExpertise.Dermatologist, "João Silva", "joao.silva", "Senha123456");		
		when(dao.getById(1)).thenReturn(defaultDoctor);
		controller = new DoctorController(dao, view);
	}
	
	@Test
	public void testErrorWhenUserIsNotADoctor() {
		controller.handleDoctor(2);
		verify(view).doctorNotFound();
		verify(view, times(0)).showWelcomeMessage(anyString());
	}
	
	@Test
	public void testShowWelcomeMessage() {
		controller.handleDoctor(1);
		verify(view).showWelcomeMessage("João Silva");
		
	}
	
	@Test
	public void testSeeSchedules_callsFilterDay() {
		when(view.selectMenuOption(anyList())).thenReturn((MenuOption) DoctorAction.SEE_SCHEDULE).thenReturn(null);
		controller.handleDoctor(1);
		verify(view).filterDay();
	}
	
	@Test
	public void testSeeScheduel_returnNullIfDontSelectDate() {
		when(view.selectMenuOption(anyList())).thenReturn((MenuOption) DoctorAction.SEE_SCHEDULE).thenReturn(null);
		when(view.filterDay()).thenReturn(null);
		Calendar calendar = new Calendar();
		
		List<Appointment> appointments = controller.filterAppointmentsByDay(calendar);
		
		assertNull(appointments);
	}
	
	@Test
	public void testShowAppointmentsEmptyDay() {
		when(view.selectMenuOption(anyList())).thenReturn((MenuOption) DoctorAction.SEE_SCHEDULE).thenReturn(null);
		when(view.filterDay()).thenReturn(new Date());
		Calendar calendar = new Calendar();
		
		List<Appointment> appointments = controller.filterAppointmentsByDay(calendar);
		
		assertTrue(appointments.isEmpty());
	}
	
	@Test
	public void testShowAppointmentsFilledDay() {
		Date date = new Date();
		Appointment ap1 = new Appointment(date, new Patient());
		Appointment ap2 = new Appointment(date, new Patient());		
		Calendar calendar = new Calendar(Arrays.asList(ap1, ap2));
		when(view.selectMenuOption(anyList())).thenReturn((MenuOption) DoctorAction.SEE_SCHEDULE).thenReturn(null);
		when(view.filterDay()).thenReturn(date);
		
		List<Appointment> appointments = controller.filterAppointmentsByDay(calendar);
		
		assertEquals(appointments.size(), 2);
		assertEquals(appointments.get(0), ap1);
	}
	
	@Test
	public void testGetExistingDoctor() {
		Doctor doctor = controller.getDoctor(1);
		assertEquals(doctor.getName(), "João Silva");
	}
	
	@Test
	public void testGetInvalidDoctor() {
		Doctor doctor = controller.getDoctor(2);
		assertNull(doctor);
	}
	
	@Test
	public void testFindAllDoctors_empty() {
		when(dao.findAll()).thenReturn(new ArrayList<>());
		assertTrue(controller.findAllDoctors().isEmpty());
	}
	
	@Test
	public void testFindAllDoctors_filled() {
		Doctor d1 = new Doctor();
		Doctor d2 = new Doctor();
		when(dao.findAll()).thenReturn(Arrays.asList(d1, d2));
		
		List<Doctor> doctors = controller.findAllDoctors();
		
		assertEquals(doctors.size(), 2);
		assertEquals(doctors.get(0), d1);
	}

}
