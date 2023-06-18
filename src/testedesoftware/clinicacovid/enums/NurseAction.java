package testedesoftware.clinicacovid.enums;

import testedesoftware.clinicacovid.view.MenuOption;

public enum NurseAction implements MenuOption {
	SEE_SCHEDULE("Ver Agenda"),
	SEE_EQUIPMENTS("Ver Equipamentos"),	
	LOGOUT("Sair");
	
	private String description;

	private NurseAction(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
