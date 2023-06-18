package testedesoftware.clinicacovid.controller;

import java.util.Date;
import java.util.List;

import testedesoftware.clinicacovid.model.Appointment;
import testedesoftware.clinicacovid.model.Calendar;
import testedesoftware.clinicacovid.view.ProfessionalView;

public abstract class ProfessionalController {

	public void showScheduledAppointments(Calendar calendar, ProfessionalView view) {		
		List<Appointment> appointments = this.filterAppointmentsByDay(calendar, view);
		
		if (appointments == null) {
			return;
		}
		
		view.showScheduledAppointments(appointments);		
	}
	
	public List<Appointment> filterAppointmentsByDay(Calendar calendar, ProfessionalView view) {
		Date filterDay = view.filterDay();		
		
		if (filterDay == null) {
			return null;
		}
		
		return calendar.filterDay(filterDay);		
	}
	
}
