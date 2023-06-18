package testedesoftware.clinicacovid.controller;

import java.util.Date;
import java.util.List;

import testedesoftware.clinicacovid.dao.PatientDao;
import testedesoftware.clinicacovid.enums.PatientAction;
import testedesoftware.clinicacovid.model.CovidTestAppointment;
import testedesoftware.clinicacovid.model.Doctor;
import testedesoftware.clinicacovid.model.DoctorAppointment;
import testedesoftware.clinicacovid.model.Patient;
import testedesoftware.clinicacovid.view.PatientView;

public class PatientController {

	PatientDao dao;
	PatientView view;
	
	private AppointmentController appointmentController;
	private DoctorController doctorController;
	
	public PatientController(PatientDao dao, PatientView view) {
		this.dao = dao;
		this.view = view;
	}
	
	public void handlePatient(Integer id) {
		
		Patient patient = this.getPatient(id);
		
		PatientAction action = view.selectMenuOption(patient.getName());
		
		switch(action) {
			case LOGOUT:
				return;
			case NEW_APPOINTMENT:
				this.newDoctorAppointment(patient);
				break;
			case SEE_SCHEDULED_APPOINTMENTS:
				this.showScheduledAppointments(patient);
				break;
		}		
	}
	
	private void showScheduledAppointments(Patient patient) {
		List<DoctorAppointment> doctorAppointments = this.appointmentController.getDoctorAppointmentsByPatient(patient.getId());
		List<CovidTestAppointment> covidTestAppointments = this.appointmentController.getCovidTestAppointmentsByPatient(patient.getId());
		this.view.showScheduledAppointments(doctorAppointments, covidTestAppointments);
	}
	
	public void newDoctorAppointment(Patient patient) {
		List<Doctor> doctors = this.doctorController.findAll(); 
		Doctor doctor = this.view.chooseDoctor(doctors);
		
		List<Date> availableDates = this.appointmentController.getAvailableTimes(new Date(), doctor);		
		Date selectedDate = this.view.selectAppointmentTime(availableDates);
				
		DoctorAppointment appointment = new DoctorAppointment(selectedDate, patient, doctor);
		this.appointmentController.createAppointment(appointment);
		this.view.showAppointmentSuccessfullyScheduled();
	}
		
	public Patient getPatient(Integer id) {
		return this.dao.getById(id);
	}

	public AppointmentController getAppointmentController() {
		return appointmentController;
	}

	public void setAppointmentController(AppointmentController appointmentController) {
		this.appointmentController = appointmentController;
	}

	public DoctorController getDoctorController() {
		return doctorController;
	}

	public void setDoctorController(DoctorController doctorController) {
		this.doctorController = doctorController;
	}
	
}
