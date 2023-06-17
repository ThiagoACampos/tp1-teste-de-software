package testedesoftware.clinicacovid.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;

import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType = DiscriminatorType.STRING)
public class Appointment {	
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
    @JoinColumn(name="idPatient", nullable=false)
	private Patient patient;
	
	private Date date;
	
	public Appointment(Date date, Patient patient) {
		this.date = date;
		this.patient = patient;
	}
	
	public Appointment() {
		
	}
	
	public Integer getId() {
		return id;
	}
	

	public Date getDate() {
		return date;
	}
	

	public void setDate(Date date) {
		this.date = date;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}
	
	public File saveToFile(File fileToSave) throws IOException {
		
        FileWriter fstream = new FileWriter(fileToSave.getAbsolutePath(), StandardCharsets.ISO_8859_1, true);
        fstream.write(this.getDate().toString());
        fstream.write(this.getPatient().getName());
        fstream.write(this.getPatient().getEmail());
        fstream.write(this.getPatient().getBirthdate().toString());
        fstream.close();
        
        return fileToSave;
        
	}
	
}
