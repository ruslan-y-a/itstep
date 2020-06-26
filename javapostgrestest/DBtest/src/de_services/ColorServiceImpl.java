package de_services;

import java.util.List;

import daos.ColorDao;
import entities.Color;
import postgres.DaoException;
import service.LogicException;

public class ColorServiceImpl implements ColorService {

	private ColorDao colorDao;

	public void setColorDao(ColorDao colorDao) {
		this.colorDao = colorDao;					
	}

	@Override
	public List<Color> findAll() throws LogicException {
		try {			
			return  colorDao.read();
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public Long save(Color variant) throws LogicException {
		try {
			Long id =variant.getId();
			if(id == null) {
				id = colorDao.create(variant);
				variant.setId(id);
			} else {
				colorDao.update(variant);
			}
			return id;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public void delete(Long id) throws LogicException {
		try {
			colorDao.delete(id);
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	@Override
	public Color read(Long id) throws LogicException {
		try {					
			return colorDao.read(id);
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	@Override
	public void delete(List<Long> ids) throws LogicException {
		try {
			for(Long id : ids) {
				colorDao.delete(id);
			}
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
}
