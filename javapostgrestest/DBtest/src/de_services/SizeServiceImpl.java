package de_services;

import java.util.List;

import daos.SizeDao;
import entities.Size;
import postgres.DaoException;
import service.LogicException;

public class SizeServiceImpl implements SizeService {

	private SizeDao sizeDao;

	public void setVariantDao(SizeDao sizeDao) {
		this.sizeDao = sizeDao;			
	}

	@Override
	public List<Size> findAll() throws LogicException {
		try {			
			return  sizeDao.read();
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public Long save(Size variant) throws LogicException {
		try {
			Long id =variant.getId();
			if(id == null) {
				id = sizeDao.create(variant);
				variant.setId(id);
			} else {
				sizeDao.update(variant);
			}
			return id;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public void delete(Long id) throws LogicException {
		try {
			sizeDao.delete(id);
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	@Override
	public Size read(Long id) throws LogicException {
		try {					
			return sizeDao.read(id);
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	@Override
	public void delete(List<Long> ids) throws LogicException {
		try {
			for(Long id : ids) {
				sizeDao.delete(id);
			}
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
}
