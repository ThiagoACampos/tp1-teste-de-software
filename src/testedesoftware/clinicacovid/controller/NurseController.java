package testedesoftware.clinicacovid.controller;

import java.util.Arrays;
import java.util.List;

import testedesoftware.clinicacovid.dao.NurseDao;
import testedesoftware.clinicacovid.enums.NurseAction;
import testedesoftware.clinicacovid.model.Equipment;
import testedesoftware.clinicacovid.model.Nurse;
import testedesoftware.clinicacovid.model.Storage;
import testedesoftware.clinicacovid.view.MenuOption;
import testedesoftware.clinicacovid.view.NurseView;

public class NurseController extends ProfessionalController {

	private NurseDao dao;
	private NurseView view;
	
	private StorageController storageController;
	
	public NurseController(NurseDao dao, NurseView view, StorageController storageController) {
		this.dao = dao;
		this.view = view;
		this.storageController = storageController;
	}
	
	public void handleNurse(Integer id) {		
		Nurse nurse = this.getNurse(id);
		
		if (nurse == null) {
			view.professionalNotFound();
			return;
		}
		
		mainLoop(nurse);
	}
	
	public void mainLoop(Nurse nurse) {
		while (true) {
			view.showWelcomeMessage(nurse.getName());
			
			List<MenuOption> menuOptions = Arrays.asList(NurseAction.values());		
			NurseAction action = (NurseAction) view.selectMenuOption(menuOptions); 
				
			if (action == NurseAction.SEE_SCHEDULE) {
				this.showScheduledAppointments(nurse.getCalendar(), view);
			} else if (action == NurseAction.SEE_EQUIPMENTS) {
				this.handleEquipments();
			} else {
				return;
			}			
		}
	}
	
	public Nurse getNurse(Integer id) {
		return this.dao.getById(id);
	}
	
	public void handleEquipments() {
		Storage storage = this.selectStorage();
		if (storage == null) {
			return;
		}
		
		List<Equipment> equipments = storage.getEquipments();
		Equipment selectedEq = view.showEquipments(equipments);
		
		if (selectedEq == null) {
			return;
		}
		
		// TODO: Inicializar EquipmentController		
	}
	
	public Storage selectStorage() {
		List<Storage> storages = storageController.findAllStorages();
		return this.view.selectStorage(storages);
	}
	
}
