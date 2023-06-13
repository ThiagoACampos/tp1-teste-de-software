package test.testedesoftware.clinicacovid.unitTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import testedesoftware.clinicacovid.enums.DoctorExpertise;
import testedesoftware.clinicacovid.model.Doctor;

public class DoctorTest {
	
	Doctor doctor;
	
	@Before
	public void setUp() {
		doctor = new Doctor("AB-123456", DoctorExpertise.Dermatologist, "João Silva", "joao.silva", "Senha123456");
	}
	
	@Test
	public void testConstructorKeepsData() {				
		assertEquals("AB-123456", doctor.getCrm());
		assertEquals(DoctorExpertise.Dermatologist, doctor.getExpertise());
		assertEquals("João Silva", doctor.getName());
		assertEquals("joao.silva", doctor.getUsername());
	}
	
	@Test
	public void testEmptyCalendar() {		
		assertTrue(doctor.getCalendar().getAppointments().isEmpty());		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidCrmError_invalidState() {
		doctor.setCrm("ABC-123456");				
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidCrmError_invalidNumber() {
		doctor.setCrm("AB-12345");				
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testInvalidCrmError_invalidFormat() {
		doctor.setCrm("AB/12345");				
	}
	
}
