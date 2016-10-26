package fi.joonas.veikkaus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fi.joonas.veikkaus.dao.StatusDao;
import fi.joonas.veikkaus.exception.VeikkausDaoException;
import fi.joonas.veikkaus.jpaentity.Status;

@Service
public class StatusService {
	
	@Autowired
	StatusDao statusDao;

	public Long insert(String statusNumber, String description) throws VeikkausDaoException {
		return statusDao.save(new Status(Integer.valueOf(statusNumber).intValue(), description)).getId();
	}
	
	public Long modify(String id, String statusNumber, String description) {
		Status status = statusDao.findOne(Long.valueOf(id)); 
		status.setStatusNumber(Integer.parseInt(statusNumber));
		status.setDescription(description);
		return statusDao.save(status).getId();
	}
	
	public boolean delete(String id) {
		boolean succeed = false;
		statusDao.delete(Long.valueOf(id));
		succeed = true;
		return succeed;
	}

}
