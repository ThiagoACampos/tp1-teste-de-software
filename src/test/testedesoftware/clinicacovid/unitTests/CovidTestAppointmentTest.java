package test.testedesoftware.clinicacovid.unitTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;

public class CovidTestAppointmentTest {

  private Nurse nurse;
  private Date appointmentDate;
  private Patient patient;

  @BeforeEach
  void setUp() {

    nurse = new Nurse("Nurse", "nurseUser", );
    appointmentDate = new Date();
    patient = new Patient("PatientName", 18, "patient@email.com", "+553199999-9999", "patientUsername");
  }

  @Test
  void testChangeNurse() {
    CovidTestAppointment covidTestAppointment = new CovidTestAppointment(nurse, appointmentDate, patient);
    Nurse newNurse = new Nurse("NewNurse", "newNurseUser", );
    covidTestAppointment.setNurse(newNurse);

    assertEquals(newNurse, covidTestAppointment.getNurse());
  }

  @Test
  void testChangeAppointmentDate() {
    CovidTestAppointment covidTestAppointment = new CovidTestAppointment(nurse, appointmentDate, patient);
    Date newAppointmentDate = new Date();
    covidTestAppointment.setAppointmentDate(newAppointmentDate);

    assertEquals(newAppointmentDate, covidTestAppointment.getAppointmentDate());
  }

  @Test
  void testChangePatient() {
    CovidTestAppointment covidTestAppointment = new CovidTestAppointment(nurse, appointmentDate, patient);
    Patient newPatient = new Patient("NewPatientName", 18, "newPatientEmail", "+553199999-9999", "newPatientUsername");
    covidTestAppointment.setPatient(newPatient);

    assertEquals(newPatient, covidTestAppointment.getPatient());
  }
}