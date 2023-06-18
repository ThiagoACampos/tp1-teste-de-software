package testedesoftware.clinicacovid.enums;

import testedesoftware.clinicacovid.view.MenuOption;

public enum DoctorAction implements MenuOption {
	SEE_SCHEDULE("Ver Agenda"),
	LOGOUT("Sair");
	
	private String description;

	private DoctorAction(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
