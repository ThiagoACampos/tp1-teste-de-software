package testedesoftware.clinicacovid.unitTests.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import testedesoftware.clinicacovid.controller.AppointmentController;
import testedesoftware.clinicacovid.controller.DoctorController;
import testedesoftware.clinicacovid.controller.NurseController;
import testedesoftware.clinicacovid.controller.PatientController;
import testedesoftware.clinicacovid.dao.PatientDao;
import testedesoftware.clinicacovid.enums.AppointmentType;
import testedesoftware.clinicacovid.enums.PatientAction;
import testedesoftware.clinicacovid.model.CovidTestAppointment;
import testedesoftware.clinicacovid.model.Doctor;
import testedesoftware.clinicacovid.model.DoctorAppointment;
import testedesoftware.clinicacovid.model.Nurse;
import testedesoftware.clinicacovid.model.Patient;
import testedesoftware.clinicacovid.view.PatientView;

public class PatientControllerTest {

	PatientController controller;
	PatientDao dao;
	PatientView view;
	AppointmentController appointmentController;
	DoctorController doctorController;
	NurseController nurseController;
	Patient defaultPatient;
	
	@Before
	public void setUp() throws Exception {
		dao = mock(PatientDao.class);
		view = mock(PatientView.class);
		appointmentController = mock(AppointmentController.class);
		doctorController = mock(DoctorController.class);
		nurseController = mock(NurseController.class);
		
		defaultPatient = new Patient();
		defaultPatient.setId(1);
		defaultPatient.setName("João Silva");
		when(dao.getById(1)).thenReturn(defaultPatient);
		
		controller = new PatientController(dao, view, appointmentController, doctorController, nurseController);
	}	
	
	@Test
	public void testGetExistingPatient() throws Exception {		
		Patient patient = controller.getPatient(1);
		assertEquals(patient.getName(), "João Silva");
	}
	
	@Test
	public void testGetInvalidPatient() throws Exception {				
		Patient patient = controller.getPatient(2);
		assertNull(patient);
	}
	
	@Test
	public void testErrorWhenUserIsNotAPatient() {
		controller.handlePatient(2);
		verify(view).showPatientNotRegistered();
		verify(view, times(0)).showWelcomeMessage(anyString());
	}
	
	@Test
	public void testShowWelcomeMessage() {
		controller.handlePatient(1);
		verify(view).showWelcomeMessage("João Silva");
		
	}
	
	@Test
	public void testPatientLogout() {
		when(view.selectMenuOption(anyList())).thenReturn(PatientAction.LOGOUT);
		
		controller.handlePatient(1);	
		
		verifyNoInteractions(appointmentController);
		verifyNoInteractions(doctorController);
		verifyNoInteractions(nurseController);		
	}
	
	@Test()
	public void testSearchAppointmentsWhenSelectSeeAppointments() {
		when(view.selectMenuOption(anyList())).thenReturn(PatientAction.SEE_SCHEDULED_APPOINTMENTS).thenReturn(null);
		
		controller.handlePatient(1);
		
		verify(appointmentController, times(1)).getCovidTestAppointmentsByPatient(1);
		verify(appointmentController, times(1)).getDoctorAppointmentsByPatient(1);
	}
	
	@Test()
	public void testShowAppointments() {
		List<DoctorAppointment> doctorAppointments = Arrays.asList(new DoctorAppointment(), new DoctorAppointment());
		List<CovidTestAppointment> covidTestAppointments = Arrays.asList(new CovidTestAppointment());
		when(view.selectMenuOption(anyList())).thenReturn(PatientAction.SEE_SCHEDULED_APPOINTMENTS).thenReturn(null);
		when(appointmentController.getDoctorAppointmentsByPatient(anyInt())).thenReturn(doctorAppointments);
		when(appointmentController.getCovidTestAppointmentsByPatient(anyInt())).thenReturn(covidTestAppointments);
		
		controller.handlePatient(1);
		
		verify(view, times(1)).showScheduledAppointments(doctorAppointments, covidTestAppointments);
	}
	
	@Test()
	public void testNewAppointment_selectAppointmentType() {
		when(view.selectMenuOption(anyList())).thenReturn(PatientAction.NEW_APPOINTMENT).thenReturn(null);		
		controller.handlePatient(1);		
		verify(view, times(1)).selectAppointmentType();
	}
	
	@Test()
	public void testNewAppointment_returnNullIfDontSelectAppointmentType() {
		when(view.selectAppointmentType()).thenReturn(null);		
		
		Boolean created = controller.handleNewAppointment(defaultPatient);
		
		assertFalse(created);
		verify(appointmentController, times(0)).createAppointment(any());
	}
	
	@Test()
	public void testDoctorAppointment_ifDontSelectDoctor() {
		when(view.selectAppointmentType()).thenReturn(AppointmentType.MEDICAL_APPOINTMENT);
		when(view.chooseDoctor(anyList())).thenReturn(null);
		
		Boolean created = controller.handleNewAppointment(defaultPatient);
		
		assertFalse(created);
		verify(doctorController).findAllDoctors();
		verify(appointmentController, times(0)).createAppointment(any());
	}
	
	@Test()
	public void testDoctorAppointment_ifDontSelectDate() {
		when(view.selectAppointmentType()).thenReturn(AppointmentType.MEDICAL_APPOINTMENT);
		when(view.chooseDoctor(anyList())).thenReturn(new Doctor());
		when(view.selectAppointmentTime(anyList())).thenReturn(null);
		
		Boolean created = controller.handleNewAppointment(defaultPatient);
		
		assertFalse(created);
		verify(appointmentController).getAvailableTimes(any(), anyList());
		verify(appointmentController, times(0)).createAppointment(any());
	}
	
	@Test()
	public void testDoctorAppointment_createAppointment() {	
		Date date = new Date();
		Doctor doctor = new Doctor();
		when(view.selectAppointmentType()).thenReturn(AppointmentType.MEDICAL_APPOINTMENT);
		when(view.chooseDoctor(anyList())).thenReturn(doctor);
		when(view.selectAppointmentTime(anyList())).thenReturn(date);
		ArgumentCaptor<DoctorAppointment> argument = ArgumentCaptor.forClass(DoctorAppointment.class);
		
		Boolean created = controller.handleNewAppointment(defaultPatient);
		
		assertTrue(created);
		verify(view).showAppointmentSuccessfullyScheduled();
		verify(appointmentController).createAppointment(argument.capture());
		assertEquals(argument.getValue().getPatient(), defaultPatient);
		assertEquals(argument.getValue().getDate(), date);
		assertEquals(argument.getValue().getDoctor(), doctor);
	}
		
	@Test()
	public void testCovidTestAppointment_ifDontSelectDate() {
		when(view.selectAppointmentType()).thenReturn(AppointmentType.COVID_TEST);
		when(view.selectAppointmentTime(anyList())).thenReturn(null);
		
		Boolean created = controller.handleNewAppointment(defaultPatient);
		
		assertFalse(created);
		verify(appointmentController).getAvailableTimes(any(), anyList());
		verify(appointmentController, times(0)).createAppointment(any());
	}
	
	@Test()
	public void testCovidTestAppointment_createAppointment() {		
		Nurse nurse = new Nurse();
		Date date = new Date();
		when(view.selectAppointmentType()).thenReturn(AppointmentType.COVID_TEST);
		when(view.selectAppointmentTime(anyList())).thenReturn(date);
		when(nurseController.findAllNurses()).thenReturn(Arrays.asList(nurse));
		ArgumentCaptor<CovidTestAppointment> argument = ArgumentCaptor.forClass(CovidTestAppointment.class);
		
		Boolean created = controller.handleNewAppointment(defaultPatient);
		
		assertTrue(created);
		verify(view).showAppointmentSuccessfullyScheduled();
		verify(appointmentController).createAppointment(argument.capture());
		assertEquals(argument.getValue().getPatient(), defaultPatient);
		assertEquals(argument.getValue().getDate(), date);
		assertEquals(argument.getValue().getNurse(), nurse);
	}
	
	
	
	
}
