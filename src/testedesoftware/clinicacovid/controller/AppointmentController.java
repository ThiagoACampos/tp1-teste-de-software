package testedesoftware.clinicacovid.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import testedesoftware.clinicacovid.dao.AppointmentDao;
import testedesoftware.clinicacovid.model.Appointment;
import testedesoftware.clinicacovid.model.Calendar;
import testedesoftware.clinicacovid.model.CovidTestAppointment;
import testedesoftware.clinicacovid.model.Doctor;
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
	
	public List<Date> getAvailableTimes(Date startDate, Doctor doctor) {
		List<Date> dates = new ArrayList<>();
		Calendar calendar = doctor.getCalendar();
		
		startDate = DateUtils.getNextBusinessHoursStart(startDate);
		
		Date date = startDate;
		
		while(dates.size() < 10) {			
			if (DateUtils.isAtBusinessHours(date)) {
				date = DateUtils.getNextBusinessHoursStart(date);
			}
			
			if (!calendar.busyAt(date)) {
				dates.add(date);
			}
			
			date = DateUtils.getNextHour(date);			
		}
		
		return dates;
	}
	
	public void createAppointment(Appointment appointment) {
		this.dao.saveOrUpdate(appointment);
	}
	
}
