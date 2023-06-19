package testedesoftware.clinicacovid.view;

public class DoctorView extends ProfessionalView {
	
	@Override
	public void professionalNotFound() {
		println("Você não é um(a) médico(a)! Por favor, tente outra forma de acesso.");
	}
	
}
