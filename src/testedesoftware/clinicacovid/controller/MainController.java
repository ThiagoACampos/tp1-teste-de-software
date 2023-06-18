package testedesoftware.clinicacovid.controller;

import testedesoftware.clinicacovid.dao.AppointmentDao;
import testedesoftware.clinicacovid.dao.DoctorDao;
import testedesoftware.clinicacovid.dao.NurseDao;
import testedesoftware.clinicacovid.dao.PatientDao;
import testedesoftware.clinicacovid.dao.StorageDao;
import testedesoftware.clinicacovid.dao.UserDao;
import testedesoftware.clinicacovid.enums.UserRole;
import testedesoftware.clinicacovid.model.User;
import testedesoftware.clinicacovid.view.DoctorView;
import testedesoftware.clinicacovid.view.LoginView;
import testedesoftware.clinicacovid.view.NurseView;
import testedesoftware.clinicacovid.view.PatientView;
import testedesoftware.clinicacovid.view.SelectModeView;

public class MainController {
	
	public void init() {
		
		SelectModeView view = new SelectModeView();
		
		LoginController loginController = new LoginController(new UserDao(), new LoginView());
		AppointmentController appointmentController = new AppointmentController(new AppointmentDao());
		StorageController storageController = new StorageController(new StorageDao());
		DoctorController doctorController = new DoctorController(new DoctorDao(), new DoctorView());		
		NurseController nurseController = new NurseController(new NurseDao(), new NurseView(), storageController);
		PatientController patientController = new PatientController(new PatientDao(), new PatientView(), appointmentController, doctorController, nurseController);
				
		while (true) {
			UserRole role = view.getUserRole();
			
			User user = loginController.handleLogin();
			if (user == null) {
				continue;
			}
			
			switch (role) {
				case Patient:
					patientController.handlePatient(user.getId());
					break;
				case Doctor:
					doctorController.handleDoctor(user.getId());
					break;
				case Nurse:
					nurseController.handleNurse(user.getId());
					break;				
			}
		}
	}
	
}
