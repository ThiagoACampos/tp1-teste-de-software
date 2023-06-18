package testedesoftware.clinicacovid.view;

import java.util.Date;
import java.util.List;

import testedesoftware.clinicacovid.enums.AppointmentType;
import testedesoftware.clinicacovid.enums.PatientAction;
import testedesoftware.clinicacovid.model.CovidTestAppointment;
import testedesoftware.clinicacovid.model.Doctor;
import testedesoftware.clinicacovid.model.DoctorAppointment;
import testedesoftware.clinicacovid.util.DateUtils;

public class PatientView extends View {
	
	
	public PatientAction selectMenuOption(String patientName) {
		print("Bem vindo(a) " + patientName);
		
		while(true) {
			print("Voce é um?");				
				
			PatientAction[] menu = PatientAction.values();
			
			for (Integer i = 0; i < menu.length; i++) {
				print((i + 1 ) + ": " + menu[i].getDescription());
			}
			
			print("Digite a opção: ");
			
			int option = in.nextInt();
			
			if (option >= 1 && option <= menu.length) {
				return menu[option - 1];
			}
			
			print("Opção inválida");
		}
	}
	
	public void showScheduledAppointments(List<DoctorAppointment> docAps, List<CovidTestAppointment> covidTestAps) {
		
		if(docAps.isEmpty() && covidTestAps.isEmpty()) {
			print("Nenhuma consulta marcada");
		}else {
			if(!covidTestAps.isEmpty()) {
				print("Exames de Covid Marcados");
				for(CovidTestAppointment ap : covidTestAps) {
					print("Enfermeiro(a): " + ap.getNurse().getName() + ", horário: " + DateUtils.dateToString(ap.getDate()));
				}
			}
			
			if(!docAps.isEmpty()) {
				print("Consultas Médicas Marcadas");
				for(DoctorAppointment ap : docAps) {
					print("Doutor(a): " + ap.getDoctor().getName() + ", horário: " + DateUtils.dateToString(ap.getDate()));
				}
			}
		}
		
		print("Digite alguma coisa para voltar");		
		this.in.next();
		return;
	}
	
	
	public AppointmentType selectAppointmentTypes() {
		while(true) {
			print("Voce quer ver horários para?");
			print("1. " + AppointmentType.MEDICAL_APPOINTMENT.getDescription());
			print("2. " + AppointmentType.COVID_TEST.getDescription());
			print("3. Voltar");
			
			int option = in.nextInt();
			switch(option) {
				case 1: return AppointmentType.MEDICAL_APPOINTMENT;
				case 2: return AppointmentType.COVID_TEST;
				case 3: return null;
				default:
					print("Opção inválida");
					break;
			}
		}
	}
	
	public Doctor chooseDoctor(List<Doctor> doctors) {
		while(true) {
			print("Qual médico(a) voce prefere ser atendido?");
			for(int i = 0; i < doctors.size(); i++) {
				print((i+1) + ". " + doctors.get(i).getName());
			}
			print((doctors.size()+1) + ". Voltar");
			
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
				print((i+1) + ". Marcar para " + DateUtils.dateToString(availableDates.get(i)));
			}
			print((availableDates.size() + 1) + ". Voltar");
			
			int option = in.nextInt();
			
			if(option == availableDates.size()+1) {
				return null;
			}else if(option >= 1 || option <= availableDates.size()) {
				return availableDates.get(option - 1);
			}
			
			print("Opção inválida");
		}
	}
	
	public void showAppointmentSuccessfullyScheduled() {
		print("Consulta marcada com sucesso!");
	}
	

}
