package testedesoftware.clinicacovid.controller;

import java.util.Arrays;
import java.util.List;

import testedesoftware.clinicacovid.dao.DoctorDao;
import testedesoftware.clinicacovid.enums.DoctorAction;
import testedesoftware.clinicacovid.model.Doctor;
import testedesoftware.clinicacovid.view.DoctorView;
import testedesoftware.clinicacovid.view.MenuOption;

public class DoctorController extends ProfessionalController {

	private DoctorDao dao;
	private DoctorView view;
	
	public DoctorController(DoctorDao dao, DoctorView view) {
		this.dao = dao;
		this.view = view;
	}

	public void handleDoctor(Integer id) {		
		Doctor doctor = this.getDoctor(id);
		
		if (doctor == null) {
			view.professionalNotFound();
			return;
		}
		
		mainLoop(doctor);
	}
	
	public void mainLoop(Doctor doctor) {
		while (true) {
			view.showWelcomeMessage(doctor.getName());
			
			List<MenuOption> menuOptions = Arrays.asList(DoctorAction.values());		
			DoctorAction action = (DoctorAction) view.selectMenuOption(menuOptions); 
				
			if (action == DoctorAction.SEE_SCHEDULE) {
				this.showScheduledAppointments(doctor.getCalendar(), view);
			} else {
				return;
			}			
		}
	}
	
	public Doctor getDoctor(Integer id) {
		return this.dao.getById(id);
	}
	
	public List<Doctor> findAllDoctors() {
		return this.dao.findAll();
	}
	
	
	
}
