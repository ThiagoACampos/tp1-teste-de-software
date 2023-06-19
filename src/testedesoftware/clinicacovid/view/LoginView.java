package testedesoftware.clinicacovid.view;

public class LoginView extends View {

	public String getUsername() {
		
		print("Seu username (Digite '0' para voltar): " );
		return readString();
	}
	
	public String getPassword() {		
		print("Sua senha (Digite '0' para voltar): " );				
		return readString();
	}
	
	public void unauthorized() {		
		println("Usuário ou senha incorretos!" );
	}	
	
	private String readString() {
		String string = in.next();		
		if(string.equals("0")) {
			return null;
		}		
		return string;
	}

}
