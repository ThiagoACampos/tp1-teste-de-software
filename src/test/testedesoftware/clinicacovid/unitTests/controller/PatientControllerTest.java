package testedesoftware.clinicacovid.unitTests.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import testedesoftware.clinicacovid.controller.AppointmentController;
import testedesoftware.clinicacovid.controller.DoctorController;
import testedesoftware.clinicacovid.controller.PatientController;
import testedesoftware.clinicacovid.dao.PatientDao;
import testedesoftware.clinicacovid.enums.PatientAction;
import testedesoftware.clinicacovid.model.CovidTestAppointment;
import testedesoftware.clinicacovid.model.DoctorAppointment;
import testedesoftware.clinicacovid.model.Patient;
import testedesoftware.clinicacovid.view.PatientView;

public class PatientControllerTest {

	PatientController controller;
	PatientDao dao;
	PatientView view;
	
	@Before
	public void setUp() throws Exception {
		dao = mock(PatientDao.class);
		view = mock(PatientView.class);
		
		Patient defaultPatient = new Patient();
		defaultPatient.setId(1);
		defaultPatient.setName("João Silva");
		when(dao.getById(1)).thenReturn(defaultPatient);
		
		controller = new PatientController(dao, view);
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
	
//	@Test
//	public void testHandlePaymentCallsLogout() {
//		when(view.selectMenuOption("João Silva")).thenReturn(PatientAction.LOGOUT);
//		controller.handlePatient(1);		
//		// TODO: Asserts
//	}
	
	@Test(expected=NullPointerException.class)
	public void testShowScheduledAppointmentsWithoutAppointmentController() {
		when(view.selectMenuOption(anyString())).thenReturn(PatientAction.SEE_SCHEDULED_APPOINTMENTS);
		controller.setAppointmentController(null);
		controller.handlePatient(1);
	}
	
	@Test()
	public void testShowScheduledAppointments() {
		List<DoctorAppointment> doctorAppointments = Arrays.asList(new DoctorAppointment(), new DoctorAppointment());
		List<CovidTestAppointment> covidTestAppointments = Arrays.asList(new CovidTestAppointment());
		AppointmentController apController = mock(AppointmentController.class);
		when(view.selectMenuOption(anyString())).thenReturn(PatientAction.SEE_SCHEDULED_APPOINTMENTS);		
		when(apController.getDoctorAppointmentsByPatient(anyInt())).thenReturn(doctorAppointments);
		when(apController.getCovidTestAppointmentsByPatient(anyInt())).thenReturn(covidTestAppointments);

		controller.setAppointmentController(apController);
		controller.handlePatient(1);
		
		Mockito.verify(view, Mockito.times(1)).showScheduledAppointments(doctorAppointments, covidTestAppointments);
	}
	
	@Test(expected=NullPointerException.class)
	public void testNewAppointmentsWithoutAppointmentController() {
		when(view.selectMenuOption(anyString())).thenReturn(PatientAction.NEW_APPOINTMENT);
		controller.setAppointmentController(null);
		controller.handlePatient(1);
	}
	
	@Test(expected=NullPointerException.class)
	public void testNewAppointmentsWithoutDoctorController() {
		when(view.selectMenuOption(anyString())).thenReturn(PatientAction.NEW_APPOINTMENT);
		controller.setDoctorController(null);
		controller.handlePatient(1);
	}
	
	public void testNewDoctorAppointment() {
		DoctorController doctorController = mock(DoctorController.class);
		AppointmentController appointmentController = mock(AppointmentController.class);
		when(view.selectMenuOption(anyString())).thenReturn(PatientAction.NEW_APPOINTMENT);
		controller.setDoctorController(doctorController);
		controller.setAppointmentController(appointmentController);
		
		controller.handlePatient(1);
		
		Mockito.verify(view, Mockito.times(1)).showAppointmentSuccessfullyScheduled();
	}
	
	
	
	
}
