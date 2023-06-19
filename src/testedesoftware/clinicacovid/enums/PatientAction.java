package testedesoftware.clinicacovid.enums;

import testedesoftware.clinicacovid.view.MenuOption;

public enum PatientAction implements MenuOption {
	NEW_APPOINTMENT("Ver Horários Disponíveis"),
	SEE_SCHEDULED_APPOINTMENTS("Ver Consultas marcadas"),
	LOGOUT("Sair");
	
	private String description;

	private PatientAction(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
	
}
