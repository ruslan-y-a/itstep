package de_services;

import java.util.List;

import daos.WebpagesDao;
import entities.Webpages;
import postgres.DaoException;
import service.LogicException;

public class WebpagesServiceImpl implements WebpagesService {
	private WebpagesDao webpagesDao;

	public void setWebpagesDao(WebpagesDao webpagesDao) {
		this.webpagesDao = webpagesDao;
	}

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
	public Webpages read(Long id) throws LogicException {
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
	
}
