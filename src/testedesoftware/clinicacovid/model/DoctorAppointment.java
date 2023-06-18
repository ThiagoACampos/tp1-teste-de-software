package testedesoftware.clinicacovid.model;

import java.util.Date;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@DiscriminatorValue("MEDICAL_APPOINTMENT")
public class DoctorAppointment extends Appointment {
	
	@ManyToOne
    @JoinColumn(name="idDoctor")
	private Doctor doctor;

	public DoctorAppointment(Date date, Patient patient, Doctor doctor) {
		super(date, patient);
		this.doctor = doctor;
	}
	
	public DoctorAppointment() {
		
	}

	public Doctor getDoctor() {
		return doctor;
	}

	public void setDoctor(Doctor doctor) {
		this.doctor = doctor;
	}
}
