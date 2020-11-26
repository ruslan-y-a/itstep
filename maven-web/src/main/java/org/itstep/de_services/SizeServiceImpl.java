package org.itstep.de_services;

import java.util.List;

import org.itstep.daos.SizeDao;
import org.itstep.entities.Size;
import org.itstep.daos.DaoException;
import org.itstep.service.LogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component
@Scope("prototype")
public class SizeServiceImpl implements SizeService {
	@Autowired	
	private SizeDao sizeDao;
	public void setVariantDao(SizeDao sizeDao) {this.sizeDao = sizeDao;}

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
	public Size findById(Long id) throws LogicException {
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
