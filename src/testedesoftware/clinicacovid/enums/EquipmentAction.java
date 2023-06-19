package testedesoftware.clinicacovid.enums;

import testedesoftware.clinicacovid.view.MenuOption;

public enum EquipmentAction implements MenuOption {
	ADD_UNITS("Adicionar unidades"),
	USE("Usar");
	
	private String description;

	private EquipmentAction(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
