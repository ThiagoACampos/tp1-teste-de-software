package testedesoftware.clinicacovid.enums;

public enum AppointmentType {
	COVID_TEST("Teste de Covid"),
	MEDICAL_APPOINTMENT("Consulta médica");
	
	private String description;

	private AppointmentType(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
