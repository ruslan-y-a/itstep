package de_services;

import java.util.List;

import daos.ImgDao;
import entities.Img;
import postgres.DaoException;
import service.LogicException;

public class ImgServiceImpl implements ImgService {
	private ImgDao imgDao;

	public void setImgDao(ImgDao imgDao) {
		this.imgDao = imgDao;
	}

	@Override
	public List<Img> findAll() throws LogicException {
		try {			
			return  imgDao.read();
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public Long save(Img img) throws LogicException {
		try {		
			Long id =img.getId();
			if(id == null) {
				id = imgDao.create(img);
				img.setId(id);
			} else {
				imgDao.update(img);
			}
			return id;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public void delete(Long id) throws LogicException {
		try {
			imgDao.delete(id);
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	@Override
	public Img read(Long id) throws LogicException {
		try {					
			return imgDao.read(id);
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	@Override
	public void delete(List<Long> ids) throws LogicException {
		try {
			for(Long id : ids) {
				imgDao.delete(id);
			}
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
}
