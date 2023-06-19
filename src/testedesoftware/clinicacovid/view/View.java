package testedesoftware.clinicacovid.view;

import java.util.List;
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
	
	public void showWelcomeMessage(String name) {
		println("Bem vindo(a) " + name + "!");
	}
	
	public MenuOption selectMenuOption(List<MenuOption> options) {
		
		while(true) {
			println("O que deseja fazer?");
			
			for (Integer i = 0; i < options.size(); i++) {
				println((i + 1 ) + ": " + options.get(i).getDescription());
			}
			
			print("Digite a opção: ");
			
			int index = in.nextInt();
			
			if (index >= 1 && index <= options.size()) {
				return options.get(index - 1);
			}
			
			println("Opção inválida");
		}
	}
	
	
}
