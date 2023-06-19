package testedesoftware.clinicacovid.enums;

public enum UserRole {
	Patient("Paciente"),
	Doctor("Médico(a)"),
	Nurse("Enfermeiro(a)");
	
	private final String description;
	
	UserRole(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
