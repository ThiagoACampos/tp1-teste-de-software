package testedesoftware.clinicacovid.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Transient;
import testedesoftware.clinicacovid.enums.NurseExpertise;

@Entity
public class Nurse extends User {

	private String coren;
	
	@Enumerated(EnumType.STRING)
	private NurseExpertise expertise;
	
	@Transient
	private List<Appointment> appointments;
	
	@Transient
	private Calendar calendar;
	
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
			this.calendar = new Calendar(this.appointments);			
		}
		return calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}
}
