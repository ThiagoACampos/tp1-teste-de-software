package test.testedesoftware.clinicacovid.unitTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;
import java.lang.IllegalArgumentException;

import testedesoftware.clinicacovid.model.Patient;

public class PatientTest {

  private String name;
  private int age;
  private String email;
  private String phone;
  private String username;

  @BeforeEach
  void setUp() {

    name = "PatientName";
    age = 18;
    email = "patient@email.com";
    phone = "3199999-9999";
    username = "patientUsername";
  }

  @Test
  void testCreatePatitent() throws Exception {
    Patient patient = new Patient(name, age, email, phone, username);

    assertEquals(name, patient.getName());
    assertEquals(age, patient.getAge());
    assertEquals(email, patient.getEmail());
    assertEquals(phone, patient.getPhone());
    assertEquals(username, patient.getUsername());
  }

  @Test
  void testNameEmpty() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Patient("", age, email, phone, username);
    });

    assertEquals(exception.getMessage(), "Name cannot be null, empty nor blank");
  }

  @Test
  void testNameNull() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Patient(null, age, email, phone, username);
    });

    assertEquals(exception.getMessage(), "Name cannot be null, empty nor blank");
  }

  @Test
  void testNameBlank() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Patient(" ", age, email, phone, username);
    });

    assertEquals(exception.getMessage(), "Name cannot be null, empty nor blank");
  }

  @Test
  void testAgeNegative() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Patient(name, -1, email, phone, username);
    });

    assertEquals(exception.getMessage(), "Age cannot be negative");
  }

  @Test
  void testEmailEmpty() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Patient(name, age, "", phone, username);
    });

    assertEquals(exception.getMessage(), "Email is not valid");
  }

  @Test
  void testEmailNull() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Patient(name, age, null, phone, username);
    });

    assertEquals(exception.getMessage(), "Email is not valid");
  }

  @Test
  void testEmailBlank() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Patient(name, age, " ", phone, username);
    });

    assertEquals(exception.getMessage(), "Email is not valid");
  }

  @Test
  void testPhoneEmpty() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Patient(name, age, email, "", username);
    });

    assertEquals(exception.getMessage(), "Phone number is not valid");
  }

  @Test
  void testPhoneNull() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Patient(name, age, email, null, username);
    });

    assertEquals(exception.getMessage(), "Phone number is not valid");
  }

  @Test
  void testPhoneBlank() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Patient(name, age, email, " ", username);
    });

    assertEquals(exception.getMessage(), "Phone number is not valid");
  }

  @Test
  void testPhoneWithoutDDD() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Patient(name, age, email, "9999-9999", username);
    });

    assertEquals(exception.getMessage(), "Phone number is not valid");
  }

  @Test
  void testPhoneWithMoreThanElevenDigits() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Patient(name, age, email, "3199999-99998", username);
    });

    assertEquals(exception.getMessage(), "Phone number is not valid");
  }

  @Test
  void testPhoneWithLessThanTenDigits() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Patient(name, age, email, "319999-999", username);
    });

    assertEquals(exception.getMessage(), "Phone number is not valid");
  }

  @Test
  void testUsernameEmpty() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Patient(name, age, email, phone, "");
    });

    assertEquals(exception.getMessage(), "Username cannot be null, empty nor blank");
  }

  @Test
  void testUsernameNull() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Patient(name, age, email, phone, null);
    });

    assertEquals(exception.getMessage(), "Username cannot be null, empty nor blank");
  }

  @Test
  void testUsernameBlank() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      new Patient(name, age, email, phone, " ");
    });

    assertEquals(exception.getMessage(), "Username cannot be null, empty nor blank");
  }
}