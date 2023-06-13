package testedesoftware.clinicacovid.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import testedesoftware.clinicacovid.exceptions.InvalidDateForSchedulingException;
import testedesoftware.clinicacovid.exceptions.NonexistingAppointmentException;
import testedesoftware.clinicacovid.exceptions.UnavailableDateForSchedulingException;

public class Calendar {
	
	private List<Appointment> appointments = new ArrayList<Appointment>();

	public Calendar() {
	}
	
	public Calendar(List<Appointment> initialAppointments) {
		if (initialAppointments != null) {
			this.appointments = initialAppointments;
		}
	}
	
	
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
			throw new NonexistingAppointmentException("Compromisso inexistente nesta agenda.");
		}
	}
	
	public List<Appointment> filterDay(Date day) {
		List<Appointment> res = new ArrayList<>();
		
		for(Appointment ap : appointments) {
			if (this.isSameDay(day, ap.getAppointmentDate())) {
				res.add(ap);
			}
		}
		return res;
	}

	public boolean busyAt(Date date) {
		for(Appointment ap : appointments) {

			// If there is an appointment with a time close to date, return true
			if(Math.abs(getMinutesDifference(date, ap.getAppointmentDate())) < 60) {
				return true;
			}
		}
		return false;
	}
	
	private boolean isSameDay(Date date1, Date date2) {
        // Cria instancias do java.util.Calendar
        java.util.Calendar cal1 = java.util.Calendar.getInstance();
        java.util.Calendar cal2 = java.util.Calendar.getInstance();

        // Define as datas nos calendarios
        cal1.setTime(date1);
        cal2.setTime(date2);

        // Compara ano, mes e dia
        return cal1.get(java.util.Calendar.YEAR) == cal2.get(java.util.Calendar.YEAR) &&
                cal1.get(java.util.Calendar.MONTH) == cal2.get(java.util.Calendar.MONTH) &&
                cal1.get(java.util.Calendar.DAY_OF_MONTH) == cal2.get(java.util.Calendar.DAY_OF_MONTH);
    }
	
	private Long getMinutesDifference(Date date1, Date date2) {
        long diffInMilliseconds = date2.getTime() - date1.getTime();
        return TimeUnit.MILLISECONDS.toMinutes(diffInMilliseconds);
    }
}
