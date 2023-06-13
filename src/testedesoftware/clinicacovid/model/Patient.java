package testedesoftware.clinicacovid.model;
import java.util.Date;
import java.util.regex.Pattern;

import jakarta.persistence.Entity;
import testedesoftware.clinicacovid.util.DateUtils;


@Entity
public class Patient extends User {
	
	private Date birthdate;
	private String email;
	private String phone;
	
	private static final String EMAIL_REGEX = "^[a-zA-Z0-9_%+-]{3,}@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*\\.[a-zA-Z]{2,}$";
	private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
	private static final String PHONE_NUMBER_REGEX = "^[1-9]{2}9?[0-9]{8}$";
    private static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile(PHONE_NUMBER_REGEX);
	
	public Patient(String name, String birthdate, String email, String phone, String username, String password) throws Exception {
		super(name, username, password);
		phone = (phone != null) ? phone.replaceAll("[^0-9]", "") : null;
		this.name = name;
		this.birthdate = DateUtils.stringToDate(birthdate);
		this.email = email;
		this.validatePatientFields();
	}

	private void validatePatientFields() throws Exception {
		if (name == null || name.trim().isEmpty()) {
			throw new IllegalArgumentException("Name cannot be null, empty nor blank");
		}
		if (birthdate.after(new Date())) {
			throw new IllegalArgumentException("Birthdate cannot in the future");
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
	
	public String getEmail() {
		return email;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public String getUsername() {
		return username;
	}

	public Date getBirthdate() {
		return birthdate;
	}

	public void setBirthdate(Date birthdate) {
		this.birthdate = birthdate;
	}
}
