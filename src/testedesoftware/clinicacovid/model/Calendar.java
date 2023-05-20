package testedesoftware.clinicacovid.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import testedesoftware.clinicacovid.exceptions.InvalidDateForSchedulingException;
import testedesoftware.clinicacovid.exceptions.UnavailableDateForSchedulingException;

public class Calendar {
	private List<Appointment> appointments = new ArrayList<Appointment>();
	
	public List<Appointment> getAppointments() {
		return appointments;
	}
	
	public void schedule(Appointment appointment) throws InvalidDateForSchedulingException, UnavailableDateForSchedulingException {
		
		if (appointment.getAppointmentDate().before(new Date())) {
			throw new InvalidDateForSchedulingException();
		}
		
		if (busyAt(appointment.getAppointmentDate())) {
			throw new UnavailableDateForSchedulingException();
		}
		
		appointments.add(appointment);
	}
	
	public void cancel(Appointment appointment) throws Exception {

		if (appointments.contains(appointment)){
			appointments.remove(appointment);
		}
		else{
			throw new Exception("Compromisso inexistente nesta agenda.");
		}
	}
	
	public List<Appointment> filterDay(Date day) {
		List<Appointment> res = new ArrayList<>();
		for(Appointment ap : appointments) {
			if(ap.getAppointmentDate().compareTo(day) == 0) {
				res.add(ap);
			}
		}
		return res;
	}

	public boolean busyAt(Date date) {
		for(Appointment ap : appointments) {

			// If there is an appointment with a time close to date, return true
			if(Math.abs(ap.getAppointmentDate().getTime() - date.getTime()) < 5*60*1000) {
				return true;
			}
		}
		return false;
	}
}
