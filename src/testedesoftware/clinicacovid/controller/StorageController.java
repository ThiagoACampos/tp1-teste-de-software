package testedesoftware.clinicacovid.controller;

import java.util.List;

import testedesoftware.clinicacovid.dao.StorageDao;
import testedesoftware.clinicacovid.model.Storage;

public class StorageController {

	private StorageDao dao;

	public StorageController(StorageDao dao) {
		this.dao = dao;
	}
	
	public List<Storage> findAllStorages() {
		return this.dao.findAll();
	}
	
}
