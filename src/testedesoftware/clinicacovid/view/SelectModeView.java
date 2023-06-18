package testedesoftware.clinicacovid.view;

import testedesoftware.clinicacovid.enums.UserRole;

public class SelectModeView extends View {

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
