package testedesoftware.clinicacovid.view;

import java.util.Date;
import java.util.List;

import testedesoftware.clinicacovid.enums.AppointmentType;
import testedesoftware.clinicacovid.model.CovidTestAppointment;
import testedesoftware.clinicacovid.model.Doctor;
import testedesoftware.clinicacovid.model.DoctorAppointment;
import testedesoftware.clinicacovid.util.DateUtils;

public class PatientView extends View {
	
	
	
	public void showScheduledAppointments(List<DoctorAppointment> docAps, List<CovidTestAppointment> covidTestAps) {
		
		if(docAps.isEmpty() && covidTestAps.isEmpty()) {
			println("\nNenhuma consulta marcada!");
		}else {
			if(!covidTestAps.isEmpty()) {
				println("\nExames de Covid Marcados\n");
				for(CovidTestAppointment ap : covidTestAps) {
					println("Enfermeiro(a): " + ap.getNurse().getName() + ", horário: " + DateUtils.dateToString(ap.getDate(), "dd/MM/yyyy HH:mm"));
				}
			}
			
			if(!docAps.isEmpty()) {
				println("\nConsultas Médicas Marcadas\n");
				for(DoctorAppointment ap : docAps) {
					println("Doutor(a): " + ap.getDoctor().getName() + ", horário: " + DateUtils.dateToString(ap.getDate(), "dd/MM/yyyy HH:mm"));
				}
			}
		}
		
		println("\nDigite alguma coisa para voltar");		
		this.in.next();
		return;
	}
	
	
	public AppointmentType selectAppointmentType() {
		while(true) {
			println("Voce quer ver horários para?");
			println("1. " + AppointmentType.MEDICAL_APPOINTMENT.getDescription());
			println("2. " + AppointmentType.COVID_TEST.getDescription());
			println("3. Voltar");
			
			int option = in.nextInt();
			switch(option) {
				case 1: return AppointmentType.MEDICAL_APPOINTMENT;
				case 2: return AppointmentType.COVID_TEST;
				case 3: return null;
				default:
					println("Opção inválida");
					break;
			}
		}
	}
	
	public Doctor chooseDoctor(List<Doctor> doctors) {
		while(true) {
			println("Por qual médico(a) voce prefere ser atendido?");
			for(int i = 0; i < doctors.size(); i++) {
				println((i+1) + ". " + doctors.get(i).getName());
			}
			println((doctors.size()+1) + ". Voltar");
			
			int option = in.nextInt();
			
			if(option == doctors.size()+1) {
				return null;
			}else if(option >= 1 && option <= doctors.size()) {
				return doctors.get(option - 1);
			}
			
			print("Opção inválida");
		}
	}
	
	public Date selectAppointmentTime(List<Date> availableDates) {
		while(true) {
			for(int i = 0; i < availableDates.size(); i++) {
				println((i+1) + ". Marcar para " + DateUtils.dateToString(availableDates.get(i), "dd/MM/yyyy HH:mm"));
			}
			println((availableDates.size() + 1) + ". Voltar");
			
			int option = in.nextInt();
			
			if(option == availableDates.size()+1) {
				return null;
			}else if(option >= 1 || option <= availableDates.size()) {
				return availableDates.get(option - 1);
			}
			
			println("Opção inválida");
		}
	}
	
	public void showAppointmentSuccessfullyScheduled() {
		println("Consulta marcada com sucesso!");
	}
	
	public void showPatientNotRegistered() {
		println("Você não possui um cadastro de paciente!");
	}
	

}
