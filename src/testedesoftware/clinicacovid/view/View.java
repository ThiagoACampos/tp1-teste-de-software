package testedesoftware.clinicacovid.view;

import java.util.Scanner;

public abstract class View {

	protected Scanner in;
		
	public View() {
		this.in = new Scanner(System.in);
	}
	
	public void print(String s) {
		System.out.print(s);
	}
	
	public void println(String s) {
		System.out.println(s);
	}
	
	
}
