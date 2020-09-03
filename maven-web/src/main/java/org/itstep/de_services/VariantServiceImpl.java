package org.itstep.de_services;

import java.util.List;

import org.itstep.daos.VariantDao;
import org.itstep.entities.Variant;
import org.itstep.postgres.DaoException;
import org.itstep.service.LogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component
@Scope("prototype")
public class VariantServiceImpl implements VariantService {
	@Autowired
	private VariantDao variantDao;
	public void setCategoryDao(VariantDao variantDao) {this.variantDao = variantDao;	}

	@Override
	public List<Variant> findAll() throws LogicException {
		try {			
			return  variantDao.read();
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public Long save(Variant variant) throws LogicException {
		try {
			Long id =variant.getId();
			if(id == null) {
				id = variantDao.create(variant);
				variant.setId(id);
			} else {
				variantDao.update(variant);
			}
			return id;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public void delete(Long id) throws LogicException {
		try {
			variantDao.delete(id);
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	@Override
	public Variant read(Long id) throws LogicException {
		try {					
			return variantDao.read(id);
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	
	@Override
	public void delete(List<Long> ids) throws LogicException {
		try {
			for(Long id : ids) {
				variantDao.delete(id);
			}
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
}
