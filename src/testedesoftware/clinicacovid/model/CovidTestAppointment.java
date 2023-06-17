package testedesoftware.clinicacovid.model;

import java.util.Date;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@DiscriminatorValue("COVID_TEST")
public class CovidTestAppointment extends Appointment {
	
	@ManyToOne
    @JoinColumn(name="idUser", nullable=false)
	private Nurse nurse;

	public CovidTestAppointment(Date date, Patient patient, Nurse nurse) {
		super(date, patient);
		this.nurse = nurse;
	}
	
	public CovidTestAppointment() {
	}

	public Nurse getNurse() {
		return nurse;
	}

	public void setNurse(Nurse nurse) {
		this.nurse = nurse;
	}
}
