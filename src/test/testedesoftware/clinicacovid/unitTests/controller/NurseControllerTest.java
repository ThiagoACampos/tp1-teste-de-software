package testedesoftware.clinicacovid.unitTests.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import testedesoftware.clinicacovid.controller.NurseController;
import testedesoftware.clinicacovid.controller.StorageController;
import testedesoftware.clinicacovid.dao.NurseDao;
import testedesoftware.clinicacovid.enums.NurseAction;
import testedesoftware.clinicacovid.enums.NurseExpertise;
import testedesoftware.clinicacovid.model.Equipment;
import testedesoftware.clinicacovid.model.Nurse;
import testedesoftware.clinicacovid.model.Storage;
import testedesoftware.clinicacovid.view.MenuOption;
import testedesoftware.clinicacovid.view.NurseView;

public class NurseControllerTest {

	NurseController controller;
	NurseDao dao;
	NurseView view;
	StorageController storageController;
	
	@Before
	public void setUp() {
		dao = mock(NurseDao.class);
		view = mock(NurseView.class);
		storageController = mock(StorageController.class);
		Nurse defaultNurse = new Nurse("João Silva", "joao.silva", "Senha123456", "SP/123.456-AE", NurseExpertise.CriticalCare);		
		when(dao.getById(1)).thenReturn(defaultNurse);
		controller = new NurseController(dao, view, storageController);
	}
	
	@Test
	public void testErrorWhenUserIsNotANurse() {
		controller.handleNurse(2);
		verify(view).professionalNotFound();
		verify(view, times(0)).showWelcomeMessage(anyString());
	}
	
	@Test
	public void testShowWelcomeMessage() {
		controller.handleNurse(1);
		verify(view).showWelcomeMessage("João Silva");
		
	}
	
	@Test
	public void testSeeSchedules_callsFilterDay() {
		when(view.selectMenuOption(anyList())).thenReturn((MenuOption) NurseAction.SEE_SCHEDULE).thenReturn(null);
		controller.handleNurse(1);
		verify(view).filterDay();
	}
	
	@Test
	public void testSeeSchedules_callsShowAppointments() {
		when(view.selectMenuOption(anyList())).thenReturn((MenuOption) NurseAction.SEE_SCHEDULE).thenReturn(null);
		when(view.filterDay()).thenReturn(new Date());
		controller.handleNurse(1);
		verify(view).showScheduledAppointments(anyList());
	}
	
	@Test
	public void testGetExistingNurse() {
		Nurse nurse = controller.getNurse(1);
		assertEquals(nurse.getName(), "João Silva");
	}
	
	@Test
	public void testGetInvalidNurse() {
		Nurse nurse = controller.getNurse(2);
		assertNull(nurse);
	}
	
	
	@Test
	public void testReturnNullIfStorageIsNotSelected() {
		when(view.selectStorage(anyList())).thenReturn(null);		
		assertNull(controller.selectStorage());
	}
	
	@Test
	public void testSelectStorage() {
		Storage s1 = new Storage();
		Storage s2 = new Storage();
		when(storageController.findAllStorages()).thenReturn(Arrays.asList(s1, s2));
		when(view.selectStorage(anyList())).thenReturn(s1);
		
		Storage selectedStorage = controller.selectStorage();
		
		assertEquals(selectedStorage, s1);
	}
	
	@Test
	public void testSeeEquipments_callsSelectStorage() {
		when(view.selectMenuOption(anyList())).thenReturn((MenuOption) NurseAction.SEE_EQUIPMENTS).thenReturn(null);
		
		controller.handleNurse(1);
		
		verify(storageController).findAllStorages();
		verify(view).selectStorage(anyList());
	}
	
	@Test
	public void testSeeEquipments() throws Exception {
		Storage s = new Storage();
		s.setSize(10);
		s.setEquipmentsAllowedToStore(Arrays.asList("Seringa", "Estetoscópio", "Termômetro"));
		s.addEquipment(new Equipment("Seringa", 1, true));
		s.addEquipment(new Equipment("Estetoscópio", 1, false));		
		when(view.selectMenuOption(anyList())).thenReturn((MenuOption) NurseAction.SEE_EQUIPMENTS).thenReturn(null);
		when(view.selectStorage(anyList())).thenReturn(s);
		
		controller.handleNurse(1);
		
		verify(view).showEquipments(s.getEquipments());		
	}
	
	

	
}
