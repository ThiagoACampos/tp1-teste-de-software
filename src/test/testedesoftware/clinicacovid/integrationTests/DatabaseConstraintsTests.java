package testedesoftware.clinicacovid.integrationTests;

import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import jakarta.persistence.EntityManager;
import testedesoftware.clinicacovid.dao.DoctorDao;
import testedesoftware.clinicacovid.dao.PatientDao;
import testedesoftware.clinicacovid.enums.DoctorExpertise;
import testedesoftware.clinicacovid.model.Doctor;
import testedesoftware.clinicacovid.model.Patient;
import testedesoftware.clinicacovid.util.HibernateUtil;

public class DatabaseConstraintsTests {

	
	List<Object> entities;
	
	@Before
	public void setUp() {
		entities = new ArrayList<>();
	}
	
	@After
	public void tearDown() {		
		EntityManager em = HibernateUtil.getSessionFactory().createEntityManager();;		
    	em.getTransaction().begin();    	
    	for (Object entity : entities) {
    		em.remove(entity);
    	}
    	em.flush();                
        em.close();		
	}

	
	@Test
	public void testSaveDoctor() {
		Doctor doctor = new Doctor("AB-123456", DoctorExpertise.Dermatologist, "João Silva", "joao", "Senha123456");
		DoctorDao dao = new DoctorDao();
		
		dao.saveOrUpdate(doctor);
		entities.add(doctor);
		
		assertNotNull(doctor.getId());		
	}
	
	
	@Test(expected=ConstraintViolationException.class)
	public void testUsernameUnique() {
		DoctorDao dao = new DoctorDao();
		Doctor doctor1 = new Doctor("AB-123456", DoctorExpertise.Dermatologist, "João Silva", "joao", "Senha123456");		
		Doctor doctor2 = new Doctor("CD-123456", DoctorExpertise.Dermatologist, "João Bosco", "joao", "Senha123456");

		dao.saveOrUpdate(doctor1);
		entities.add(doctor1);
		dao.saveOrUpdate(doctor2);
		entities.add(doctor2);
	}

	@Test(expected=ConstraintViolationException.class)
	public void testCRMUnique() {
		DoctorDao dao = new DoctorDao();
		Doctor doctor1 = new Doctor("AB-123456", DoctorExpertise.Dermatologist, "João Silva", "joao.silva", "Senha123456");		
		Doctor doctor2 = new Doctor("AB-123456", DoctorExpertise.Dermatologist, "João Bosco", "joao.bosco", "Senha123456");

		dao.saveOrUpdate(doctor1);
		entities.add(doctor1);		
		dao.saveOrUpdate(doctor2);
		entities.add(doctor2);
	}

	@Test
	public void testSavePatient() throws Exception {
		PatientDao dao = new PatientDao();
		Patient patient = new Patient("Carlos Ferreira", "10/04/1995", "carlosferreira@gmail.com", "(31) 912341234", "carlos.ferreira", "Senha123456");
		
		dao.saveOrUpdate(patient);
		entities.add(patient);
		
		assertNotNull(patient.getId());
	}
	
	@Test(expected=ConstraintViolationException.class)
	public void testPatientEmailUnique() throws Exception {
		PatientDao dao = new PatientDao();
		Patient p1 = new Patient("Carlos Ferreira", "10/04/1995", "carlos@gmail.com", "(31) 912341234", "carlos.ferreira", "Senha123456");
		Patient p2 = new Patient("Carlos Nogueira", "11/02/1999", "carlos@gmail.com", "(31) 945674567", "carlos.nogueira", "Senha123456");
				
		dao.saveOrUpdate(p1);
		entities.add(p1);
		
		dao.saveOrUpdate(p2);
		entities.add(p2);
	}
	
}
