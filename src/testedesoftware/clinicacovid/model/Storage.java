package testedesoftware.clinicacovid.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Storage {
	private List<Equipment> equipments;
	private Integer size;
	private List<String> equipmentsAllowedToStore;
	
	public String exceptionMaximumStorageSizeReached = "A capacidade máxima de armazenamento do Armazém foi atingida";
	public String exceptionEquipmentNotAllowed = "Esse equipamento não pode ser armazenado";

	public Storage() {
		equipments = new ArrayList<>();
	}
	
	public void setSize(Integer size) {
		this.size = size;
	}
	
	public Integer getSize() {
		return size;
	}
	
	public void setEquipmentsAllowedToStore(List<String> equipmentsNames) {
		equipmentsAllowedToStore = new ArrayList<String>();
		equipmentsAllowedToStore.addAll(equipmentsNames);
	}
	
	public List<String> getEquipmentsAllowedToStore(){
		return equipmentsAllowedToStore;
	}

	public List<Equipment> getEquipments() {
		return equipments;
	}
	
	public Integer getQuantityOfAllEquipmentsInStore() {
		Integer sum = 0;
		
		for(Equipment eq: getEquipments()) {
			sum += eq.getQuantity();
		}
		
		return sum;
	}

	public List<Equipment> missingEquipments() {
		List<Equipment> missing = new ArrayList<>();
		for(Equipment eq : equipments) {
			if(!eq.canUse()) {
				missing.add(eq);
			}
		}
		return missing;
	}

	public void useEquipments(List<Equipment> equipments) throws Exception {
		for(Equipment eq : equipments) {
			eq.use();
		}
	}

	public void addUnits(int eqIndex, int units) throws Exception {
		checkStorageSizeReached(units);
		equipments.get(eqIndex).addUnits(units);
	}

	public void addEquipment(Equipment eq) throws Exception{
		checkStorageSizeReached(eq.getQuantity());
		checkEquipmentAllowed(eq.getName());
		equipments.add(eq);
	}
	
	private void checkStorageSizeReached(Integer units) throws Exception{
		if(getQuantityOfAllEquipmentsInStore() + units > size) {
			throw new Exception(this.exceptionMaximumStorageSizeReached);
		}
	}
	
	private void checkEquipmentAllowed(String equipmentName) throws Exception{
		if(!equipmentsAllowedToStore.contains(equipmentName)) {
			throw new Exception(this.exceptionEquipmentNotAllowed);
		}
	}

	public File saveEquipmentsToFile(File fileToSave) throws IOException {

        FileWriter fstream = new FileWriter(fileToSave.getAbsolutePath(), StandardCharsets.ISO_8859_1,true);

        for(Integer i = 0; i < this.equipments.size(); i++) {
        	Equipment current_equipment = this.getEquipments().get(i);
	        fstream.write(current_equipment.getName());
	        fstream.write(current_equipment.getQuantity());
	        fstream.write(Boolean.toString(current_equipment.canUse()));
        }

        fstream.close();

        return fileToSave;

	}
}
