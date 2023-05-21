package testedesoftware.clinicacovid.model;
import java.lang.IllegalArgumentException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Patient {
	
	private String username;
	private String name;
	private int age;
	private String email;
	private String phone;

	private static final String EMAIL_REGEX = "^[a-zA-Z0-9_%+-]{3,}@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z]{2,}$";
	private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
	private static final String PHONE_NUMBER_REGEX = "^[1-9]{2}9?[0-9]{8}$";
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(PHONE_NUMBER_REGEX);
	
	public Patient(String name, int age, String email, String phone, String username) {
		super();
		phone = phone.replaceAll("[^0-9]", "");
		this.username = username;
		this.name = name;
		this.age = age;
		this.email = email;
		this.phone = phone;
		_validatePatientFields();
	}

	private void _validatePatientFields() {
		if (name == null || name.trim().isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null, empty nor blank");
		}
		if (age < 0) {
			throw new IllegalArgumentException("Age cannot be negative");
		}
		if (email == null || EMAIL_PATTERN.matcher(email).matches() == false) {
			throw new IllegalArgumentException("Email is not valid");
		}
		if (phone == null || PHONE_NUMBER_PATTERN.matcher(phone).matches() == false) {
			throw new IllegalArgumentException("Phone number is not valid");
		}
		if (username == null || username.trim().isEmpty()) {
			throw new IllegalArgumentException("Username cannot be null, empty nor blank");
		}
	}
	
	public String getName() {
		return name;
	}
	
	public int getAge() {
		return age;
	}
	
	public String getEmail() {
		return email;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public String getUsername() {
		return username;
	}
}
