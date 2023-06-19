package testedesoftware.clinicacovid.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;
import testedesoftware.clinicacovid.enums.DoctorExpertise;

@Entity
public class Doctor extends User {
	
	
	private static final String CRM_REGEX = "^[A-Z]{2}-\\d{6}$";
	
	@Column(unique=true)
	private String crm;
	
	@Enumerated(EnumType.STRING)
	private DoctorExpertise expertise;
	
	@OneToMany(mappedBy="doctor", fetch = FetchType.EAGER)
	private List<DoctorAppointment> appointments;
	
	@Transient
	private Calendar calendar;
	
	public Doctor() {
		
	}
	
	public Doctor(String crm, DoctorExpertise expertise, String name, String username, String password) {
		super(name, username, password);
		this.setCrm(crm);
		this.setExpertise(expertise);
	}
	
	public String getCrm() {
		return crm;
	}
	
	public void setCrm(String crm) {
		
		if (crm == null) {
			throw new IllegalArgumentException("CRM can't be null");
		}
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
