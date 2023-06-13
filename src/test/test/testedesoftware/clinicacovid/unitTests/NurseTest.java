package test.testedesoftware.clinicacovid.unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import testedesoftware.clinicacovid.enums.NurseExpertise;
import testedesoftware.clinicacovid.model.Nurse;

public class NurseTest {

	Nurse nurse;
	
	@Before
	public void setUp() {
		nurse = new Nurse("João Silva", "joao.silva", "Senha123456", "SP/123.456-AE", NurseExpertise.CriticalCare);
	}
	
	@Test
	public void testConstructorKeepsData() {				
		assertEquals(nurse.getCoren(), "SP/123.456-AE");
		assertEquals(nurse.getExpertise(), NurseExpertise.CriticalCare);
		assertEquals(nurse.getName(), "João Silva");
		assertEquals(nurse.getUsername(), "joao.silva");
	}
	
	@Test
	public void testEmptyCalendar() {		
		assertTrue(nurse.getCalendar().getAppointments().isEmpty());		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidCoreError_nullValue() {
		nurse.setCoren(null);
	}
}
