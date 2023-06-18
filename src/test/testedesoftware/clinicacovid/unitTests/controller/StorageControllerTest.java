package testedesoftware.clinicacovid.unitTests.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import testedesoftware.clinicacovid.controller.StorageController;
import testedesoftware.clinicacovid.dao.StorageDao;
import testedesoftware.clinicacovid.model.Storage;

public class StorageControllerTest {

	StorageController controller;
	StorageDao dao;
	
	@Before()
	public void setUp() {
		dao = mock(StorageDao.class); 
		controller = new StorageController(dao);
	}
	
	@Test
	public void testFindAll_empty() {
		when(dao.findAll()).thenReturn(new ArrayList<>());
		assertTrue(controller.findAllStorages().isEmpty());
	}
	
	@Test
	public void testFindAll_filled() {
		Storage s1 = new Storage();
		Storage s2 = new Storage();
		when(dao.findAll()).thenReturn(Arrays.asList(s1, s2));
		
		List<Storage> doctors = controller.findAllStorages();
		
		assertEquals(doctors.size(), 2);
		assertEquals(doctors.get(0), s1);
		assertEquals(doctors.get(1), s2);
	}
	
}
