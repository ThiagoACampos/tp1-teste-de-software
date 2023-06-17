package testedesoftware.clinicacovid.dao.impl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import testedesoftware.clinicacovid.dao.GenericDao;
import testedesoftware.clinicacovid.dao.UserDao;
import testedesoftware.clinicacovid.model.User;

public class UserDaoImpl extends GenericDao<User> implements UserDao {
	
	public User getByUsername(String username) {
        EntityManager em = getFactory().createEntityManager();
        em.getTransaction().begin();
        
        Query query = em.createQuery("select u from User u where u.username = :username");
        query.setParameter("username", username);
        
        User user = (User) query.getSingleResult();
        
        em.close();
        
        return user;
    }
}
