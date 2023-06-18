package testedesoftware.clinicacovid.view;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import testedesoftware.clinicacovid.model.Appointment;
import testedesoftware.clinicacovid.model.Patient;
import testedesoftware.clinicacovid.util.DateUtils;

public abstract class ProfessionalView extends View {
	
	public Date filterDay() {
		while (true) {
			print("Informe a data (dd/mm/yyyy): ");
			
			String day = in.next();
			
			if (day.equals("0")) {
				return null;
			}
			
			try {
				return DateUtils.stringToDate(day);
			} catch(ParseException e) {
				println("Formato inválido!");
			}
		}
	}
	
	public void showScheduledAppointments(List<Appointment> appointments) {
		
		if (appointments.isEmpty()) {
			println("\nNão há consultas marcadas nessa data!\n");
			return;
		}
		
		println("\nConsultas Marcadas: \n");		
		
	    for(Appointment ap : appointments) {
	      Patient pat = ap.getPatient();
	      String date = DateUtils.dateToString(ap.getDate(), "dd/MM/yyyy HH:mm");
	      println(date + ": " + pat.getName() + " (" + pat.getPhone() + ")");
	    }
	    
	    println("");
	}
	
	public void professionalNotFound() {
		println("Você não é um(a) profissional! Por favor, tente outra forma de acesso.");
	}
}
