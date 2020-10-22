package org.itstep.de_services;

import java.util.List;

import org.itstep.daos.ImgDao;
import org.itstep.entities.Img;
import org.itstep.daos.DaoException;
import org.itstep.service.LogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ImgServiceImpl implements ImgService {
	@Autowired
	private ImgDao imgDao;
	public void setImgDao(ImgDao imgDao) {this.imgDao = imgDao;}

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
				//img.setId(id);
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
	public Img findById(Long id) throws LogicException {
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
