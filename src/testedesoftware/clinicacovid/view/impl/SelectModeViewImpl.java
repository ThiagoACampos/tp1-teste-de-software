package testedesoftware.clinicacovid.view.impl;

import testedesoftware.clinicacovid.enums.UserRole;
import testedesoftware.clinicacovid.view.SelectModeView;
import testedesoftware.clinicacovid.view.View;

public class SelectModeViewImpl extends View implements SelectModeView {

	public UserRole getUserRole() {
		while(true) {
			print("Voce é um?");				
				
			UserRole[] roleList = UserRole.values();
			
			for (Integer i = 0; i < roleList.length; i++) {
				print((i + 1 ) + ": " + roleList[i].getDescription());
			}
			
			print("Digite a opção: ");
			
			int option = in.nextInt();
			
			if (option >= 1 && option <= roleList.length) {
				return roleList[option - 1];
			}
			
			print("Opção inválida");
		}		
	}
}
