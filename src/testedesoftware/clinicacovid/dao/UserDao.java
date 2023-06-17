package testedesoftware.clinicacovid.dao;

import testedesoftware.clinicacovid.model.User;

public interface UserDao {
	
	public User getByUsername(String username);
}
