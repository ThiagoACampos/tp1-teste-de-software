package testedesoftware.clinicacovid.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import testedesoftware.clinicacovid.enums.NurseExpertise;

@Entity
public class Nurse extends User {

	private String coren;
	
	@Enumerated(EnumType.STRING)
	private NurseExpertise expertise;
	
	@OneToMany(mappedBy="nurse", fetch = FetchType.EAGER)
	private List<CovidTestAppointment> appointments;
	
	@Transient
	private Calendar calendar;
	
	public Nurse() {
		
	}
	
	public Nurse(String name, String username, String password, String coren, NurseExpertise expertise) {
		super(name, username, password);
		this.setCoren(coren);
		this.setExpertise(expertise);
	}

	public String getCoren() {
		return coren;
	}

	public void setCoren(String coren) {
		if (coren == null) {
			throw new IllegalArgumentException("Coren can't be null");
		}		
		this.coren = coren;
	}

	public NurseExpertise getExpertise() {
		return expertise;
	}

	public void setExpertise(NurseExpertise expertise) {
		this.expertise = expertise;
	}

	public Calendar getCalendar() {
		if (calendar == null) {			
			@SuppressWarnings("unchecked")
			List<Appointment> appointments = (List<Appointment>) (List<? extends Appointment>) this.appointments;
			this.calendar = new Calendar(appointments);				
		}
		return calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}
}
