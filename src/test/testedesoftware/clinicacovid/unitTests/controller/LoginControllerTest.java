package testedesoftware.clinicacovid.unitTests.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import testedesoftware.clinicacovid.controller.LoginController;
import testedesoftware.clinicacovid.dao.UserDao;
import testedesoftware.clinicacovid.exceptions.UnauthorizedException;
import testedesoftware.clinicacovid.model.User;
import testedesoftware.clinicacovid.view.LoginView;

public class LoginControllerTest {

	LoginController controller;
	UserDao dao;
	LoginView view;
	
	@Before
	public void setUp() {
		dao = mock(UserDao.class);
		view = mock(LoginView.class);
		when(dao.getByUsername("validusername")).thenReturn(new User("Valid User", "validusername", "password"));
		controller = new LoginController(dao, view);		
	}
	
	@Test(expected=UnauthorizedException.class)
	public void testUserNotFound() throws UnauthorizedException {
		controller.auth("notFound", "123");		
	}
	
	@Test(expected=UnauthorizedException.class)
	public void testIncorrectPassword() throws UnauthorizedException {
		controller.auth("validusername", "wrongpassword");		
	}
	
	@Test()
	public void testSuccessfulAuthorization() throws UnauthorizedException {
		User user = controller.auth("validusername", "password");
		assertEquals(user.getName(), "Valid User");
		assertEquals(user.getUsername(), "validusername");
		assertEquals(user.getPassword(), "password");
	}
	
	public void testReturnNullIfUsernameIsNotProvided() {
		when(view.getUsername()).thenReturn(null);
		User user = controller.handleLogin();
		assertNull(user);
	}
	
	public void testReturnNullIfPasswordIsNotProvided() {
		when(view.getPassword()).thenReturn(null);		
		User user = controller.handleLogin();		
		assertNull(user);
	}
	
	public void tesIfUserIsNotAuthenticated() {
		when(view.getPassword()).thenReturn("notFound");
		when(view.getPassword()).thenReturn("123");
		
		User user = controller.handleLogin();
		
		assertNull(user);
		Mockito.verify(view, Mockito.times(1)).unauthorized();
	}
}
