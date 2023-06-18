package testedesoftware.clinicacovid.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;
import testedesoftware.clinicacovid.dao.UserDao;
import testedesoftware.clinicacovid.model.User;

public class UserDao extends GenericDao<User> {
	
	public User getByUsername(String username) {
		try {
	        EntityManager em = getFactory().createEntityManager();
	        em.getTransaction().begin();
	        
	        Query query = em.createQuery("select u from User u where u.username = :username");
	        query.setParameter("username", username);
	        
	        User user = (User) query.getSingleResult();
	        
	        em.close();
	        
	        return user;
	        
		} catch(NoResultException e) {
			return null;
		}
    }
}
