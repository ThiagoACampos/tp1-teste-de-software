package testedesoftware.clinicacovid.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Equipment {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;
	private Integer quantity;
	private Boolean consumable;
	private Integer max;
	
	@ManyToOne
    @JoinColumn(name="idStorage", nullable=false)
	private Storage storage;

	public static final String exceptionEquipmentNoLongerAvailableOrCannotBeUsed = "Equipamento sem unidades disponíveis ou não consumível";
	public static final String exceptionEquipmentQuantityMustBePositive = "Quantidade de um equipamento deve ser positiva.";
	public static final String exceptionEquipmentQuantityExceedsMaximum = "A capacidade máxima de armazenamento é foi execedida.";

	public Equipment(String name, Integer quantity, Boolean consumable) {
		this.name = name;
		this.quantity = quantity;
		this.consumable = consumable;
		this.max = 100;
	}	

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public Integer getMax() {
		return max;
	}
	
	public Storage getStorage() {
		return storage;
	}

	public void setStorage(Storage storage) {
		this.storage = storage;
	}

	public void setQuantity(int quantity) throws Exception {
		this.checkQuantity(quantity);
		this.quantity = quantity;
	}

	public void setStatus(boolean equipmentStatus) {
		this.consumable = equipmentStatus;
	}

	public void use() throws Exception {
		if(this.canUse()) {
			quantity--;
		}
		else{
			throw new Exception(exceptionEquipmentNoLongerAvailableOrCannotBeUsed);
		}
	}

	public Boolean canUse() {
		return quantity > 0 && consumable;
	}

	public void addUnits(int qtt) throws Exception {
		this.checkQuantity(qtt);
		if (this.quantity + qtt > this.max) {
			throw new Exception(exceptionEquipmentQuantityExceedsMaximum);
		}
		quantity += qtt;
	}

	private void checkQuantity(int quantity) throws Exception {
		if (quantity < 0) {
			throw new Exception(exceptionEquipmentQuantityMustBePositive);
		}
		if (quantity > this.max) {
			throw new Exception(exceptionEquipmentQuantityExceedsMaximum);
		}
	}

}
