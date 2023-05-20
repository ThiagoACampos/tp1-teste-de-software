package testedesoftware.clinicacovid.model;

public class Equipment {

	private String name;
	private int quantity;
	private boolean consumable;
	private int max;

	public String exceptionEquipmentNoLongerAvailableOrCannotBeUsed = "Equipamento sem unidades disponíveis ou não consumível";
	public String exceptionEquipmentQuantityMustBePositive = "Quantidade de um equipamento deve ser positiva.";
	public String exceptionEquipmentQuantityExceedsMaximum = "A capacidade máxima de armazenamento é " + this.max + ".";

	public Equipment(String name, int quantity, boolean consumable) {
		super();
		this.name = name;
		this.quantity = quantity;
		this.consumable = consumable;
		this.max = 100;
	}

	public String getName() {
		return name;
	}

	public int getQuantity() {
		return quantity;
	}

	public int getMax() {
		return max;
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
			throw new Exception(this.exceptionEquipmentNoLongerAvailableOrCannotBeUsed);
		}
	}

	public boolean canUse() {
		return quantity > 0 && consumable;
	}

	public void addUnits(int qtt) throws Exception {
		this.checkQuantity(qtt);
		if (this.quantity + qtt > this.max) {
			throw new Exception(this.exceptionEquipmentQuantityExceedsMaximum);
		}
		quantity += qtt;
	}

	private void checkQuantity(int quantity) throws Exception {
		if (quantity < 0) {
			throw new Exception(this.exceptionEquipmentQuantityMustBePositive);
		}
		if (quantity > this.max) {
			throw new Exception(this.exceptionEquipmentQuantityExceedsMaximum);
		}
	}
}
