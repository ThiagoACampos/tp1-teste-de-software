package testedesoftware.clinicacovid.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import testedesoftware.clinicacovid.dao.PatientDao;
import testedesoftware.clinicacovid.enums.AppointmentType;
import testedesoftware.clinicacovid.enums.PatientAction;
import testedesoftware.clinicacovid.model.Calendar;
import testedesoftware.clinicacovid.model.CovidTestAppointment;
import testedesoftware.clinicacovid.model.Doctor;
import testedesoftware.clinicacovid.model.DoctorAppointment;
import testedesoftware.clinicacovid.model.Nurse;
import testedesoftware.clinicacovid.model.Patient;
import testedesoftware.clinicacovid.view.MenuOption;
import testedesoftware.clinicacovid.view.PatientView;

public class PatientController {

	private PatientDao dao;
	private PatientView view;	
	private AppointmentController appointmentController;
	private DoctorController doctorController;
	private NurseController nurseController;
	
	public PatientController(PatientDao dao, PatientView view, AppointmentController appointmentController, DoctorController doctorController, NurseController nurseController) {
		this.dao = dao;
		this.view = view;
		this.appointmentController = appointmentController;
		this.doctorController = doctorController;
		this.nurseController = nurseController;
	}
	
	public void handlePatient(Integer id) {		
		Patient patient = this.getPatient(id);
		
		if (patient == null) {
			view.showPatientNotRegistered();
			return;
		}
		
		mainLoop(patient);
	}
	
	public void mainLoop(Patient patient) {
		while (true) {
			view.showWelcomeMessage(patient.getName());
			
			List<MenuOption> menuOptions = Arrays.asList(PatientAction.values());		
			PatientAction action = (PatientAction) view.selectMenuOption(menuOptions); 
				
			if (action == null) {
				return;
			}
			
			switch(action) {
				case LOGOUT:
					return;
				case NEW_APPOINTMENT:
					this.handleNewAppointment(patient);
					break;
				case SEE_SCHEDULED_APPOINTMENTS:
					this.showScheduledAppointments(patient);
					break;
			}
		}
	}
	

	public Patient getPatient(Integer id) {
		return this.dao.getById(id);
	}
	
	public Boolean handleNewAppointment(Patient patient) {
		AppointmentType type = view.selectAppointmentType();
		
		if (type == AppointmentType.MEDICAL_APPOINTMENT) {
			return this.newDoctorAppointment(patient);
		} else if (type == AppointmentType.COVID_TEST) {
			return this.newCovidTestAppointment(patient);
		}
		
		return false;
		
	}
	
	private void showScheduledAppointments(Patient patient) {
		List<DoctorAppointment> doctorAppointments = this.appointmentController.getDoctorAppointmentsByPatient(patient.getId());
		List<CovidTestAppointment> covidTestAppointments = this.appointmentController.getCovidTestAppointmentsByPatient(patient.getId());
		this.view.showScheduledAppointments(doctorAppointments, covidTestAppointments);
	}
	
	private Boolean newDoctorAppointment(Patient patient) {
		List<Doctor> doctors = this.doctorController.findAllDoctors(); 
		Doctor doctor = this.view.chooseDoctor(doctors);
		if (doctor == null) {
			return false;
		}
				
		List<Date> availableDates = this.appointmentController.getAvailableTimes(new Date(), Arrays.asList(doctor.getCalendar()));		
		Date selectedDate = this.view.selectAppointmentTime(availableDates);
		if (selectedDate == null) {
			return false;
		}
				
		DoctorAppointment appointment = new DoctorAppointment(selectedDate, patient, doctor);
		this.appointmentController.createAppointment(appointment);
		this.view.showAppointmentSuccessfullyScheduled();
		return true;
	}
	
	private Boolean newCovidTestAppointment(Patient patient) { 
		List<Nurse> nurses = this.nurseController.findAllNurses();
		List<Calendar> calendars = nurses.stream().map(Nurse::getCalendar).collect(Collectors.toList());
				
		List<Date> availableDates = this.appointmentController.getAvailableTimes(new Date(), calendars);		
		Date selectedDate = this.view.selectAppointmentTime(availableDates);
		if (selectedDate == null) {
			return false;
		}
			
		Calendar selectedCalendar = this.appointmentController.getCalendarAvailableAtDate(calendars, selectedDate);
		Nurse selectedNurse = nurses.stream().filter(n -> n.getCalendar().equals(selectedCalendar)).findFirst().get();		
		
		for (Nurse n : nurses) {
			if (selectedCalendar.equals(n.getCalendar())) {
				selectedNurse = n;
			}
		}
				
		CovidTestAppointment appointment = new CovidTestAppointment(selectedDate, patient, selectedNurse);
		this.appointmentController.createAppointment(appointment);
		this.view.showAppointmentSuccessfullyScheduled();
		return true;
	}
	
}
