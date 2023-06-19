package testedesoftware.clinicacovid.view;

import testedesoftware.clinicacovid.enums.UserRole;

public class SelectModeView extends View {

	public UserRole getUserRole() {
		while(true) {
			println("Voce é um?");				
				
			UserRole[] roleList = UserRole.values();
			
			for (Integer i = 0; i < roleList.length; i++) {
				println((i + 1 ) + ": " + roleList[i].getDescription());
			}
			
			print("Digite a opção ('0' para encerrar): ");
			
			int option = in.nextInt();
			
			if (option == 0) {
				return null;
			}
			
			if (option >= 1 && option <= roleList.length) {
				return roleList[option - 1];
			}
			
			println("Opção inválida");
		}		
	}
}
