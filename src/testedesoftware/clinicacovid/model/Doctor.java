package testedesoftware.clinicacovid.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Transient;
import testedesoftware.clinicacovid.enums.DoctorExpertise;

@Entity
public class Doctor extends User {
	
	
	private static final String CRM_REGEX = "^[A-Z]{2}-\\d{6}$";
	
	private String crm;
	
	@Enumerated(EnumType.STRING)
	private DoctorExpertise expertise;
	
    @Transient
	private List<Appointment> appointments;
	
	@Transient
	private Calendar calendar;
	
	public Doctor(String crm, DoctorExpertise expertise, String name, String username, String password) {
		super(name, username, password);
		this.setCrm(crm);
		this.setExpertise(expertise);
	}
	
	public String getCrm() {
		return crm;
	}
	
	public void setCrm(String crm) {
		if (!crm.matches(CRM_REGEX)) {
			throw new IllegalArgumentException("Invalid CRM");
		}
		this.crm = crm;
		
	}

	public DoctorExpertise getExpertise() {
		return expertise;
	}

	public void setExpertise(DoctorExpertise expertise) {
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
