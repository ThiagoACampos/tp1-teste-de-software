package testedesoftware.clinicacovid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import testedesoftware.clinicacovid.enums.DoctorExpertise;
import testedesoftware.clinicacovid.model.Appointment;
import testedesoftware.clinicacovid.model.Calendar;
import testedesoftware.clinicacovid.model.CovidTestAppointment;
import testedesoftware.clinicacovid.model.Doctor;
import testedesoftware.clinicacovid.model.DoctorAppointment;
import testedesoftware.clinicacovid.model.Equipment;
import testedesoftware.clinicacovid.model.Nurse;
import testedesoftware.clinicacovid.model.Patient;
import testedesoftware.clinicacovid.model.Storage;

enum Mode { Doctor, Nurse, Patient }
public class DataController {
	Mode currentMode = null;
	
	private Storage storage;
	private List<Doctor> doctors = new ArrayList<>();
	private List<Nurse> nurses = new ArrayList<>();
	private List<Patient> patients = new ArrayList<>();
	
	public DataController() throws Exception{
		// Initialize initial data
		// TODO: Fetch from the database

		Doctor d1 = new Doctor("AB-123456", DoctorExpertise.Dermatologist, "João Silva", "joao.silva", "Senha123456");
		Doctor d2 = new Doctor("BC-123456", DoctorExpertise.Dermatologist, "Maria Oliveira", "maria.oliveira", "Senha123456");
		Doctor d3 = new Doctor("DE-123456", DoctorExpertise.Dermatologist, "Carlos Moreira", "carlos.moreira", "Senha123456");

		doctors.add(d1);
		doctors.add(d2);
		doctors.add(d3);
//		
//		Nurse n1 = new Nurse("Marcos", "marcos");
//		Nurse n2 = new Nurse("Camila", "camila");
//		Nurse n3 = new Nurse("Gabriel", "gabriel");
//		Nurse n4 = new Nurse("Fernanda", "fernanda");
//		
//		nurses.add(n1);
//		nurses.add(n2);
//		nurses.add(n3);
//		nurses.add(n4);
//		
		Patient p1 = new Patient("Bernardo Nunes", "15/12/2000", "bernardo@gmail.com", "31912341234", "bernardo.nunes", "123");
//		Patient p2 = new Patient("Otávio",  20, "vini@gmail.com", "31999999999", "otavio");
//		Patient p3 = new Patient("Bernardo",  20, "vini@gmail.com", "31999999999", "bernardo");
//		Patient p4 = new Patient("Fernando",  20, "vini@gmail.com", "31999999999", "fernando");
//		Patient p5 = new Patient("Thiago",  20, "vini@gmail.com", "31999999999", "thiago");
		patients.add(p1);
//		patients.add(p2);
//		patients.add(p3);
//		patients.add(p4);
//		patients.add(p5);
		
//		
		storage = new Storage();
		storage.setSize(50);
		storage.setEquipmentsAllowedToStore(Arrays.asList("Máscara Descartável","Teste Covid","Álcool em gel"));
		storage.addEquipment(new Equipment("Máscara Descartável", 4, true));
		storage.addEquipment(new Equipment("Teste Covid", 20, true));
		storage.addEquipment(new Equipment("Álcool em gel", 10, true));
	}
	
	HashMap<Mode, String> getModes() {
		HashMap<Mode, String> modes = new HashMap<Mode, String>();
		modes.put(Mode.Doctor, "Médico(a)");
		modes.put(Mode.Nurse, "Enfermeiro(a)");
		modes.put(Mode.Patient, "Paciente");
		return modes;
	}

	public void setMode(Mode mode) {
		currentMode = mode;
	}

	public Nurse getNurse(String username) {
		for(Nurse n : nurses) {
			if(n.getUsername().equals(username)) {
				return n;
			}
		}
		return null;
	}

	public void goToMenu() {
		currentMode = null;
	}

	public Doctor getDoctor(String username) {
		for(Doctor n : doctors) {
			if(n.getUsername().equals(username)) {
				return n;
			}
		}
		return null;
	}

	public Patient getPatient(String username) {
		for(Patient n : patients) {
			if(n.getUsername().equals(username)) {
				return n;
			}
		}
		return null;
	}

	public List<Doctor> getDoctors() {
		return doctors;
	}

	boolean validDate(Date date) {
		if(date.getHours() < 8 || date.getHours() > 18) return false;
		return true;
	}
	
	@SuppressWarnings("deprecation")
	public List<Date> getAvailableTimesStarting(Date date, Doctor doctor) {
		List<Date> dates = new ArrayList<>();
		Calendar calendar = doctor.getCalendar();
		
		while(!validDate(date)) {
			// Advance 1 hour
			date = new Date(date.getTime() + 60*60*1000);
		}
		
		while(dates.size() < 10) {
			if(!calendar.busyAt(date)) {
				dates.add(date);
			}
			
			// Advance 1 hour
			do {
				date = new Date(date.getTime() + 60*60*1000);
			} while(!validDate(date));
		}
		return dates;
	}

	public List<DoctorAppointment> getDoctorAppointmentsFor(Patient patient) {
		List<DoctorAppointment> aps = new ArrayList<>();
		
		for(Doctor doc : doctors) {
			List<Appointment> apsP = doc.getCalendar().getAppointments();
			for(Appointment ap : apsP) {
				if(ap.getPatient().equals(patient)) {
					aps.add((DoctorAppointment) ap);
				}
			}
		}
		
		return aps;
	}
	
	public List<CovidTestAppointment> getCovidTestAppointmentsFor(Patient patient) {
		List<CovidTestAppointment> aps = new ArrayList<>();
		
		return aps;
	}
}
