package org.itstep.de_services;

import java.util.List;

import org.itstep.daos.ColorDao;
import org.itstep.entities.Color;
import org.itstep.daos.DaoException;
import org.itstep.service.LogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ColorServiceImpl implements ColorService {
	@Autowired	
	private ColorDao colorDao;
	public void setColorDao(ColorDao colorDao) {this.colorDao = colorDao;}

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
	public Color findById(Long id) throws LogicException {
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
