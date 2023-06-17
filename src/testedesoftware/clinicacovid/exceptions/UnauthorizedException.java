package testedesoftware.clinicacovid.exceptions;

public class UnauthorizedException extends Exception {

	private static final long serialVersionUID = 1L;

	public UnauthorizedException() {
		super("Usuário ou senha incorretos!");
	}
}
