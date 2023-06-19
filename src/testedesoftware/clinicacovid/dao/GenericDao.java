package testedesoftware.clinicacovid.dao;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.SessionFactory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import testedesoftware.clinicacovid.util.HibernateUtil;

public class GenericDao<T> {

    private SessionFactory factory;
    private final Class<T> typeParameterClass;

    @SuppressWarnings("unchecked")
	protected GenericDao() {
    	this.factory = HibernateUtil.getSessionFactory();
    	this.typeParameterClass = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0]);
    }

	public SessionFactory getFactory() {
		return factory;
	}

    public void saveOrUpdate(T entity) {
    	EntityManager em = getFactory().createEntityManager();
    	em.getTransaction().begin();
    	try {
    		em.persist(entity);
        	em.flush();
    	} catch (Exception e) {
    		em.getTransaction().rollback();
    		throw e;
    	} finally {
            em.close();	
    	}
    }

    public void delete(T entity) {
    	EntityManager em = getFactory().createEntityManager();
    	em.getTransaction().begin();
    	try {
    		em.remove(entity);
        	em.flush();
    	} catch(Exception e) {
        	em.getTransaction().rollback();
    		throw e;
    	} finally {
            em.close();
    	}
    }

    public T getById(Integer id) {
    	EntityManager em = getFactory().createEntityManager();
    	try {
    		T object = em.find(typeParameterClass, id);
    		return object;
    	} catch (Exception e) {
        	em.getTransaction().rollback();
        	throw e;
    	} finally {
    		em.close();
    	}
    }
    
    public List<T> findAll() {
    	EntityManager em = getFactory().createEntityManager();
    	try {
        	Query q = em.createQuery("select t from " + typeParameterClass.getSimpleName() + " t");
            List<T> result = (List<T>) q.getResultList();
        	return result;
    	} catch (Exception e) {
    		throw e;
    	} finally {
        	em.close();
    	}
    }

}
