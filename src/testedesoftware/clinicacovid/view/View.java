package testedesoftware.clinicacovid.view;

import java.util.Scanner;

public abstract class View {

	Scanner in;
		
	public View() {
		this.in = new Scanner(System.in);
	}
	
	public void print(String s) {
		System.out.println(s);
	}
	
	public abstract Object render();
}
