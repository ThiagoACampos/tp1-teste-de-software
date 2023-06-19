package testedesoftware.clinicacovid.controller;

import testedesoftware.clinicacovid.dao.UserDao;
import testedesoftware.clinicacovid.exceptions.UnauthorizedException;
import testedesoftware.clinicacovid.model.User;
import testedesoftware.clinicacovid.view.LoginView;

public class LoginController {

	LoginView view;
	UserDao dao;
	
	public LoginController(UserDao dao, LoginView view) {
		this.view = view;
		this.dao = dao;
	}

	public User handleLogin() {
		String username = view.getUsername();
		
		if (username == null) {
			return null;
		}
		
		String password = view.getPassword();
		
		if (password == null) {
			return null;
		}
		
		try {
			return this.auth(username, password);			
		} catch (UnauthorizedException exception) {
			view.unauthorized();
			return null;
		}
	}

	public User auth(String username, String password) throws UnauthorizedException {
		User user = dao.getByUsername(username);
		
		if (user == null) {
			throw new UnauthorizedException();
		}
		
		if (!user.getPassword().equals(password)) {
			throw new UnauthorizedException();
		}
		
		return user;
	}
	
}
