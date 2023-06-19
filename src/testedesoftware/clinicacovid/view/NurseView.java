package testedesoftware.clinicacovid.view;

import java.util.List;

import testedesoftware.clinicacovid.model.Equipment;
import testedesoftware.clinicacovid.model.Storage;

public class NurseView extends ProfessionalView {

	@Override
	public void professionalNotFound() {
		println("Você não é um(a) enfermeiro(a)! Por favor, tente outra forma de acesso.");
	}
	
	public Storage selectStorage(List<Storage> storages) {
		while (true) {
			if (storages.isEmpty()) {
				println("\nEssa clínica não possui nenhum armazém cadastrado!\n");
				return null;
			}
			
			println("\nArmazéns: \n");		
		    
		    for (Integer i=0; i < storages.size(); i++) {
				println((i + 1 ) + ": " + storages.get(i).getName());
		    }
		    
		    println("");
		    print("Selecione um armazém ('0' para voltar): ");
			
			int index = in.nextInt();
			
			if (index == 0) {
				return null;
			} else if (index >= 1 && index <= storages.size()) {
				return storages.get(index - 1);
			}
			
			println("Opção inválida");	    
		    println("");
		}
	}
	
	public Equipment showEquipments(List<Equipment> equipments) {
		while (true) {
			if (equipments.isEmpty()) {
				println("\nNão há nenhum equipamento cadastrado nesse armazém!\n");
				return null;
			}
			
			println("\nEquipamentos: \n");		
		    
		    for (Integer i=0; i < equipments.size(); i++) {
				println(
					(i + 1 ) + ": " + equipments.get(i).getName() 
					+ " Qtd.: " + equipments.get(i).getQuantity() 
					+ " Máx.: " + equipments.get(i).getMax()
				);
		    }
		    
		    println("");
		    print("Selecione um equipamento ('0' para voltar): ");
			
			int index = in.nextInt();
			
			if (index == 0) {
				return null;
			} else if (index >= 1 && index <= equipments.size()) {
				return equipments.get(index - 1);
			}
			
			println("Opção inválida");	    
		    println("");
		}
	}
	
}
