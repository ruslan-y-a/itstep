package de_services;

import java.util.List;

import daos.CurrencyDao;
import entities.Currency;
import postgres.DaoException;
import service.LogicException;

public class CurrencyServiceImpl implements CurrencyService {
	private CurrencyDao currencyDao;

	public void setCurrencyDao(CurrencyDao currencyDao) {
		this.currencyDao = currencyDao;
	}

	@Override
	public List<Currency> findAll() throws LogicException {
		try {			
			return  currencyDao.read();
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public Long save(Currency currency) throws LogicException {
		try {
			Long id =currency.getId();
			if(id== null) {
				id = currencyDao.create(currency);
				currency.setId(id);
			} else {
				currencyDao.update(currency);
			}
			return id;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public void delete(Long id) throws LogicException {
		try {
			currencyDao.delete(id);
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	@Override
	public Currency read(Long id) throws LogicException {
		try {					
			return currencyDao.read(id);
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	@Override
	public void delete(List<Long> ids) throws LogicException {
		try {
			for(Long id : ids) {
				currencyDao.delete(id);
			}
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
}
