package test.testedesoftware.clinicacovid.unitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import testedesoftware.clinicacovid.model.Patient;
import testedesoftware.clinicacovid.util.DateUtils;

public class PatientTest {

  private String name;
  private String birthdate;
  private String email;
  private String phone;
  private String username;
  private String password;
  
  @BeforeEach
  void setUp() {

    name = "PatientName";
    birthdate = "01/01/2000";
    email = "patient@email.com";
    phone = "3199999-9999";
    username = "patientUsername";
    password = "TesteDeSoftware123";
  }

  @Test
  void testCreatePatitent() throws Exception {
    Patient patient = new Patient(name, birthdate, email, phone, username, "");

    assertEquals(name, patient.getName());
    assertEquals(DateUtils.stringToDate(birthdate), patient.getBirthdate());
    assertEquals(email, patient.getEmail());
    assertEquals(phone.replaceAll("[^0-9]", ""), patient.getPhone());
    assertEquals(username, patient.getUsername());
  }

  @Test
  void testNameEmpty() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Patient("", birthdate, email, phone, username, password);
    });

    assertEquals(exception.getMessage(), "Name cannot be null, empty nor blank");
  }

  @Test
  void testNameNull() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Patient(null, birthdate, email, phone, username, password);
    });

    assertEquals(exception.getMessage(), "Name cannot be null, empty nor blank");
  }

  @Test
  void testNameBlank() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Patient(" ", birthdate, email, phone, username, password);
    });

    assertEquals(exception.getMessage(), "Name cannot be null, empty nor blank");
  }

  @Test
  void testDateInTheFuture() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Patient(name, "01/01/2200", email, phone, username, password);
    });

    assertEquals(exception.getMessage(), "Birthdate cannot in the future");
  }

  @Test
  void testEmailEmpty() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Patient(name, birthdate, "", phone, username, password);
    });

    assertEquals(exception.getMessage(), "Email is not valid");
  }

  @Test
  void testEmailNull() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Patient(name, birthdate, null, phone, username, password);
    });

    assertEquals(exception.getMessage(), "Email is not valid");
  }

  @Test
  void testEmailBlank() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Patient(name, birthdate, " ", phone, username, password);
    });

    assertEquals(exception.getMessage(), "Email is not valid");
  }

  @Test
  void testPhoneEmpty() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Patient(name, birthdate, email, "", username, password);
    });

    assertEquals(exception.getMessage(), "Phone number is not valid");
  }

  @Test
  void testPhoneNull() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Patient(name, birthdate, email, null, username, password);
    });

    assertEquals(exception.getMessage(), "Phone number is not valid");
  }

  @Test
  void testPhoneBlank() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Patient(name, birthdate, email, " ", username, password);
    });

    assertEquals(exception.getMessage(), "Phone number is not valid");
  }

  @Test
  void testPhoneWithoutDDD() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Patient(name, birthdate, email, "9999-9999", username, password);
    });

    assertEquals(exception.getMessage(), "Phone number is not valid");
  }

  @Test
  void testPhoneWithMoreThanElevenDigits() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Patient(name, birthdate, email, "3199999-99998", username, password);
    });

    assertEquals(exception.getMessage(), "Phone number is not valid");
  }

  @Test
  void testPhoneWithLessThanTenDigits() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Patient(name, birthdate, email, "319999-999", username, password);
    });

    assertEquals(exception.getMessage(), "Phone number is not valid");
  }

  @Test
  void testUsernameEmpty() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Patient(name, birthdate, email, phone, "", password);
    });

    assertEquals(exception.getMessage(), "Username cannot be null, empty nor blank");
  }

  @Test
  void testUsernameNull() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Patient(name, birthdate, email, phone, null, password);
    });

    assertEquals(exception.getMessage(), "Username cannot be null, empty nor blank");
  }

  @Test
  void testUsernameBlank() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Patient(name, birthdate, email, phone, " ", password);
    });

    assertEquals(exception.getMessage(), "Username cannot be null, empty nor blank");
  }
}