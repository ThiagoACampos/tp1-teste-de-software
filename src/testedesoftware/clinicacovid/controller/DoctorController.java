package testedesoftware.clinicacovid.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import testedesoftware.clinicacovid.dao.DoctorDao;
import testedesoftware.clinicacovid.enums.DoctorAction;
import testedesoftware.clinicacovid.model.Appointment;
import testedesoftware.clinicacovid.model.Calendar;
import testedesoftware.clinicacovid.model.Doctor;
import testedesoftware.clinicacovid.view.DoctorView;
import testedesoftware.clinicacovid.view.MenuOption;

public class DoctorController {

	private DoctorDao dao;
	private DoctorView view;
	
	public DoctorController(DoctorDao dao, DoctorView view) {
		this.dao = dao;
		this.view = view;
	}

	public void handleDoctor(Integer id) {		
		Doctor doctor = this.getDoctor(id);
		
		if (doctor == null) {
			view.doctorNotFound();
			return;
		}
		
		mainLoop(doctor);
	}
	
	public void mainLoop(Doctor doctor) {
		while (true) {
			view.showWelcomeMessage(doctor.getName());
			
			List<MenuOption> menuOptions = Arrays.asList(DoctorAction.values());		
			DoctorAction action = (DoctorAction) view.selectMenuOption(menuOptions);				
			
			switch(action) {
				case LOGOUT:
					return;
				case SEE_SCHEDULE:
					this.showScheduledAppointments(doctor.getCalendar());
					break;
			}
		}
	}
	
	public Doctor getDoctor(Integer id) {
		return this.dao.getById(id);
	}
	
	public List<Doctor> findAllDoctors() {
		return this.dao.findAll();
	}
	
	public void showScheduledAppointments(Calendar calendar) {		
		List<Appointment> appointments = this.filterAppointmentsByDay(calendar);
		
		if (appointments == null) {
			return;
		}
		
		view.showScheduledAppointments(appointments);		
	}
	
	public List<Appointment> filterAppointmentsByDay(Calendar calendar) {
		Date filterDay = this.view.filterDay();		
		
		if (filterDay == null) {
			return null;
		}
		
		return calendar.filterDay(filterDay);		
	}
	
}
