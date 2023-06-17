package testedesoftware.clinicacovid.view.impl;

import testedesoftware.clinicacovid.view.LoginView;
import testedesoftware.clinicacovid.view.View;

public class LoginViewImpl extends View implements LoginView {

	public String getUsername() {
		
		print("Seu username (Digite '0' para voltar): " );
		return readString();
	}
	
	public String getPassword() {		
		print("Sua senha (Digite '0' para voltar): " );				
		return readString();
	}
	
	public void unauthorized() {		
		print("Usuário ou senha incorretos" );
	}	
	
	private String readString() {
		String string = in.next();		
		if(string.equals("0")) {
			return null;
		}		
		return string;
	}

}
