package test.testedesoftware.clinicacovid.unitTests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import testedesoftware.clinicacovid.enums.NurseExpertise;
import testedesoftware.clinicacovid.model.CovidTestAppointment;
import testedesoftware.clinicacovid.model.Nurse;
import testedesoftware.clinicacovid.model.Patient;

public class CovidTestAppointmentTest {

  private Nurse nurse;
  private Date appointmentDate;
  private Patient patient;

  @BeforeEach
  void setUp() throws Exception{
    nurse = new Nurse("Nurse", "nurseUser", "nurse123456", "SP/123.456-AE", NurseExpertise.CriticalCare);
    appointmentDate = new Date();
    patient = new Patient("PatientName", "01/01/2000", "patient@email.com", "3199999-9999", "patientUsername", "123");
  }

  @Test
  void testChangeNurse() {
    CovidTestAppointment covidTestAppointment = new CovidTestAppointment(nurse, appointmentDate, patient);
    Nurse newNurse = new Nurse("NewNurse", "newNurseUser", "nurse123456", "SP/456.789-AE", null);
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
  void testChangePatient() throws Exception {
    CovidTestAppointment covidTestAppointment = new CovidTestAppointment(nurse, appointmentDate, patient);
    Patient newPatient = new Patient("NewPatientName", "01/01/2000", "newPatient@email.com", "3199999-9999", "newPatientUsername", "");
    covidTestAppointment.setPatient(newPatient);

    assertEquals(newPatient, covidTestAppointment.getPatient());
  }
}