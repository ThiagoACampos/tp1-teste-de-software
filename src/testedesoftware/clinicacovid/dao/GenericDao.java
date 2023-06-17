package testedesoftware.clinicacovid.dao;

import java.lang.reflect.ParameterizedType;

import org.hibernate.SessionFactory;

import jakarta.persistence.EntityManager;
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
    	em.persist(entity);
    	em.flush();                
        em.close();
    }

    public void delete(T entity) {
    	EntityManager em = getFactory().createEntityManager();
    	em.getTransaction().begin();    	
    	em.remove(entity);
    	em.flush();                
        em.close();
    }

    public T getById(Integer id) {
    	EntityManager em = getFactory().createEntityManager();
    	T object = em.find(typeParameterClass, id);
    	em.close();
    	return object;
    }

}
