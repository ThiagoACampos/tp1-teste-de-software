package testedesoftware.clinicacovid.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import testedesoftware.clinicacovid.dao.AppointmentDao;
import testedesoftware.clinicacovid.model.Appointment;
import testedesoftware.clinicacovid.model.Calendar;
import testedesoftware.clinicacovid.model.CovidTestAppointment;
import testedesoftware.clinicacovid.model.DoctorAppointment;
import testedesoftware.clinicacovid.util.DateUtils;

public class AppointmentController {

	AppointmentDao dao;

	public AppointmentController(AppointmentDao dao) {
		this.dao = dao;
	}
	
	public List<DoctorAppointment> getDoctorAppointmentsByPatient(Integer idPatient) {
		return this.dao.getDoctorAppointmentsByPatient(idPatient);
	}
	
	public List<CovidTestAppointment> getCovidTestAppointmentsByPatient(Integer idPatient) {
		return this.dao.getCovidTestAppointmentsByPatient(idPatient);
	}
	
	public List<Date> getAvailableTimes(Date startDate, List<Calendar> calendars) {
		List<Date> dates = new ArrayList<>();		
		
		startDate = DateUtils.getNextHour(startDate);
		Date date = DateUtils.getNextBusinessHoursStart(startDate);
		
		while(dates.size() < 10) {			
			if (!DateUtils.isAtBusinessHours(date)) {
				date = DateUtils.getNextBusinessHoursStart(date);
			}			
						
			if (calendars.isEmpty()) {
				dates.add(date);
			} else {
				for (Calendar calendar : calendars) {
					if (!calendar.busyAt(date)) {
						dates.add(date);
						break;
					}	
				}
			}
						
			date = DateUtils.getNextHour(date);			
		}
		
		return dates;
	}
	
	public Calendar getCalendarAvailableAtDate(List<Calendar> calendars, Date date) {
		Optional<Calendar> calendar = calendars.stream().filter(c -> c.busyAt(date) == false).findFirst();
		return calendar.orElse(null);	
	}
	
	public void createAppointment(Appointment appointment) {
		this.dao.saveOrUpdate(appointment);
	}
	
}
