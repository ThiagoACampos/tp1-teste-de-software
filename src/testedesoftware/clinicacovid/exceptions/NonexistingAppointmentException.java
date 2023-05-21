package testedesoftware.clinicacovid.exceptions;

public class NonexistingAppointmentException extends Exception {

	private static final long serialVersionUID = 1L;

	public NonexistingAppointmentException(String message) {
		super(message);
	}
}
