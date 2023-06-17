package testedesoftware.clinicacovid.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Transient;

@Entity
public class Storage {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Integer size;
		
    @OneToMany(mappedBy="storage", cascade = CascadeType.PERSIST)
	private List<Equipment> equipments;
	
	@Transient
	private List<String> equipmentsAllowedToStore;
	
	public static final String exceptionMaximumStorageSizeReached = "A capacidade máxima de armazenamento do Armazém foi atingida";
	
	public static final String exceptionEquipmentNotAllowed = "Esse equipamento não pode ser armazenado";

	public Storage() {
		setEquipments(new ArrayList<>());
	}	

	public Integer getId() {
		return id;
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
	
	public void setEquipments(List<Equipment> equipments) {
		this.equipments = equipments;
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
		for(Equipment eq : getEquipments()) {
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
		getEquipments().get(eqIndex).addUnits(units);
	}

	public void addEquipment(Equipment eq) throws Exception{
		checkStorageSizeReached(eq.getQuantity());
		checkEquipmentAllowed(eq.getName());
		getEquipments().add(eq);
		eq.setStorage(this);
	}
	
	private void checkStorageSizeReached(Integer units) throws Exception{
		if(getQuantityOfAllEquipmentsInStore() + units > size) {
			throw new Exception(exceptionMaximumStorageSizeReached);
		}
	}
	
	private void checkEquipmentAllowed(String equipmentName) throws Exception{
		if(!equipmentsAllowedToStore.contains(equipmentName)) {
			throw new Exception(exceptionEquipmentNotAllowed);
		}
	}

	public File saveEquipmentsToFile(File fileToSave) throws IOException {

        FileWriter fstream = new FileWriter(fileToSave.getAbsolutePath(), StandardCharsets.ISO_8859_1,true);

        for(Integer i = 0; i < this.getEquipments().size(); i++) {
        	Equipment current_equipment = this.getEquipments().get(i);
	        fstream.write(current_equipment.getName());
	        fstream.write(current_equipment.getQuantity());
	        fstream.write(Boolean.toString(current_equipment.canUse()));
        }

        fstream.close();

        return fileToSave;

	}

}
