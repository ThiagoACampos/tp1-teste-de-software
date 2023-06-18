package testedesoftware.clinicacovid.dao;

import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import testedesoftware.clinicacovid.model.Appointment;
import testedesoftware.clinicacovid.model.CovidTestAppointment;
import testedesoftware.clinicacovid.model.DoctorAppointment;

public class AppointmentDao extends GenericDao<Appointment> {

	public List<DoctorAppointment> getDoctorAppointmentsByPatient(Integer idPatient) {	
        EntityManager em = getFactory().createEntityManager();
        em.getTransaction().begin();
        
        Query query = em.createQuery("select ap from DoctorAppointment ap where ap.patient.id = :id");
        query.setParameter("id", idPatient);
        
        List<DoctorAppointment> resultList = (List<DoctorAppointment>) query.getResultList();
        
        em.close();
        
        return resultList;
    }
	
	public List<CovidTestAppointment> getCovidTestAppointmentsByPatient(Integer idPatient) {	
        EntityManager em = getFactory().createEntityManager();
        em.getTransaction().begin();
        
        Query query = em.createQuery("select ap from CovidTestAppointment ap where ap.patient.id = :id");
        query.setParameter("id", idPatient);
        
        List<CovidTestAppointment> resultList = (List<CovidTestAppointment>) query.getResultList();
        
        em.close();
        
        return resultList;
    }

}
