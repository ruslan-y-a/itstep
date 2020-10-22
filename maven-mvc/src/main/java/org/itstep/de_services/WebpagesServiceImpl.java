package org.itstep.de_services;

import java.util.List;

import org.itstep.daos.WebpagesDao;
import org.itstep.entities.Webpages;
import org.itstep.daos.DaoException;
import org.itstep.service.LogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component
@Scope("prototype")
public class WebpagesServiceImpl implements WebpagesService {
	@Autowired	
	private WebpagesDao webpagesDao;
	public void setWebpagesDao(WebpagesDao webpagesDao) {this.webpagesDao = webpagesDao;}

	@Override
	public List<Webpages> findAll() throws LogicException {
		try {			
			return  webpagesDao.read();
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public Long save(Webpages webpages) throws LogicException {
		try {
			Long id =webpages.getId();
			if(id == null) {
				id = webpagesDao.create(webpages);
				webpages.setId(id);
			} else {
				webpagesDao.update(webpages);
			}
			return id;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public void delete(Long id) throws LogicException {
		try {
			webpagesDao.delete(id);
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	@Override
	public Webpages findById(Long id) throws LogicException {
		try {					
			return webpagesDao.read(id);
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	@Override
	public void delete(List<Long> ids) throws LogicException {
		try {
			for(Long id : ids) {
				webpagesDao.delete(id);
			}
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	@Override
	public Webpages readEntityId(String uri) throws LogicException {
		try {					
			return webpagesDao.readEntityId(uri);
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	@Override
	public Webpages findByEntityId(Long eid, String entity) throws LogicException {
		try {					
			return webpagesDao.fingByEntityId(eid, entity);
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
}
