package testedesoftware.clinicacovid;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import testedesoftware.clinicacovid.model.Appointment;
import testedesoftware.clinicacovid.model.Calendar;
import testedesoftware.clinicacovid.model.Doctor;
import testedesoftware.clinicacovid.model.Patient;

public class DoctorController extends ModelController<Doctor>  {

  DoctorController(DataController dataController, Scanner in) {
    this.dataController = dataController;
    this.in = in; 
  }

  @Override
  protected Doctor getUser(String username) {
    return this.dataController.getDoctor(username);
  }

  public void handleDoctor() {

    Doctor doctor = getUser();

    if (doctor == null) {
			return;
		}

		handleDocLoop: 
    while(true) {
      
      printDoctorMenu(doctor.getName());
			
			int option = in.nextInt();	
			switch(option) {
				case 1:
					Calendar cal = doctor.getCalendar();
					List<Appointment> aps = cal.getAppointments();
					if(!aps.isEmpty() ) {
						printScheduledAppointments(aps);
					} else {
						println("Não há consultas marcadas\n");
					}
					break;
				case 2:
          dataController.goToMenu();
					break handleDocLoop;
				default:
					println("Opção inválida");
					continue;
			}
			
		}
	}

  private void printDoctorMenu(String name) {
    println("Bem vindo(a) " + name + "!");
		println("1. Ver Agenda");
		println("2. Voltar");
  }

  private void printScheduledAppointments(List<Appointment> aps) {
    println("Consultas Marcadas: ");
    for(Appointment ap : aps) {
      Patient pat = ap.getPatient();
      Date date = ap.getDate();
      println(date.toLocaleString() + ": " 
          + pat.getName() + " (" + pat.getPhone() + ")");
    }
    println("");
  }

}