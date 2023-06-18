package testedesoftware.clinicacovid.controller;

import java.util.List;

import testedesoftware.clinicacovid.dao.DoctorDao;
import testedesoftware.clinicacovid.model.Doctor;

public class DoctorController {

	private DoctorDao dao;
	
	public DoctorController(DoctorDao dao) {
		this.dao = dao;
	}

	public List<Doctor> findAll() {
		return this.dao.findAll();
	}
}
