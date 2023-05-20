package test.testedesoftware.clinicacovid.unitTests;

import org.junit.jupiter.api.Test;

import testedesoftware.clinicacovid.model.Equipment;
import testedesoftware.clinicacovid.model.Storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;

public class StorageTest {
	private Storage storage;
	
	@BeforeEach
	void setUp() {
		storage = new Storage();
		storage.setSize(50);
		storage.setEquipmentsAllowedToStore(Arrays.asList("Seringa","PCR"));
	}

	@Test
	void testAddEquipment() throws Exception{
		Equipment seringa = new Equipment("Seringa", 25, false);
		Equipment pcr = new Equipment("PCR", 20, true);
		storage.addEquipment(seringa);
		storage.addEquipment(pcr);

		assertEquals(2, storage.getEquipments().size());
	}

	@Test
	void testUseEquipmentsSuccess() throws Exception {
		Equipment pcr = new Equipment("PCR", 30, true);
		storage.addEquipment(pcr);
		storage.useEquipments(Arrays.asList(pcr));

		assertEquals(1, storage.getEquipments().size());
		assertEquals(29, pcr.getQuantity());
	}
	
	@Test
	void testUseEquipmentsFailureBecauseEquipmentQuantity() throws Exception {
		Equipment seringa = new Equipment("Seringa", 1, true);
		Equipment pcr = new Equipment("PCR", 0, true);
		storage.addEquipment(seringa);
		storage.addEquipment(pcr);
		
		Exception exception = assertThrows(Exception.class, () -> {
			storage.useEquipments(Arrays.asList(seringa,pcr));
		});
		
		assertEquals(pcr.exceptionEquipmentNoLongerAvailableOrCannotBeUsed, exception.getMessage());
	}

	@Test
	void testAddUnitsSuccess() throws Exception {
		Equipment seringa = new Equipment("Seringa", 0, true);
		Equipment pcr = new Equipment("PCR", 0, true);
		storage.addEquipment(seringa);
		storage.addEquipment(pcr);
		
		storage.addUnits(0, 10);

		assertEquals(10, seringa.getQuantity());
		assertEquals(0, pcr.getQuantity());
	}
	
	@Test
	void testAddUnitsFailureBecauseEquipmentIndexNotExists() throws Exception {
		Equipment seringa = new Equipment("Seringa", 0, true);
		Equipment pcr = new Equipment("PCR", 0, true);
		storage.addEquipment(pcr);
		storage.addEquipment(seringa);
		
		assertThrows(IndexOutOfBoundsException.class, () -> storage.addUnits(2, 10));
	}

	@Test
	void testMissingEquipments() throws Exception{
		Equipment seringa = new Equipment("Seringa", 0, false);
		Equipment pcr = new Equipment("PCR", 7, true);
		storage.addEquipment(seringa);
		storage.addEquipment(pcr);

		final var missingEquipments = storage.missingEquipments();

		assertEquals(1, missingEquipments.size());
		assertTrue(missingEquipments.contains(seringa));
	}
	
	@Test
	void testMaximumStorageSize() throws Exception {
		Equipment seringa = new Equipment("Seringa", 25, false);
		Equipment pcr = new Equipment("PCR", 20, true);
		storage.setSize(40);
		storage.addEquipment(seringa);
		
		Exception exception = assertThrows(Exception.class, () -> {
			storage.addEquipment(pcr);
		});
		
		assertEquals(storage.exceptionMaximumStorageSizeReached, exception.getMessage());
	}
	
	@Test
	void testEquipmentNotAllowedToStore() throws Exception {
		Equipment agulha = new Equipment("Agulha", 5, false);
		
		Exception exception = assertThrows(Exception.class, () -> {
			storage.addEquipment(agulha);
		});
		
		assertEquals(storage.exceptionEquipmentNotAllowed, exception.getMessage());
	}
	
	@Test
	void testSetEquipmentsAllowedToStore() {
		Equipment seringa = new Equipment("Seringa", 25, false);
		Equipment pcr = new Equipment("PCR", 20, true);
		
		storage.setEquipmentsAllowedToStore(Arrays.asList(seringa.getName(),pcr.getName()));
		
		assertTrue(storage.getEquipmentsAllowedToStore().contains(seringa.getName()));
		assertTrue(storage.getEquipmentsAllowedToStore().contains(pcr.getName()));
	}
	
	@Test
	void testGetQuantityOfAllEquipmentsInStore() throws Exception{
		Equipment seringa = new Equipment("Seringa", 25, false);
		Equipment pcr = new Equipment("PCR", 20, true);
		
		storage.addEquipment(seringa);
		storage.addEquipment(pcr);
		
		assertEquals(45,storage.getQuantityOfAllEquipmentsInStore());
	}

}
