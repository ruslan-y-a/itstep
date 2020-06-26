package de_services;

import java.util.List;

import daos.CurrencyDao;
import daos.CountryDao;
import entities.Country;
import entities.Currency;
import postgres.DaoException;
import service.LogicException;

public class CountrySeviceImpl implements CountrySevice {
	//Currency
	private CurrencyDao currencyDao;
	private CountryDao countryDao;

	public void setCurrencyDao(CurrencyDao currencyDao) {
		this.currencyDao = currencyDao;}
	public void setCountryDao(CountryDao countryDao) {		
		this.countryDao= countryDao;}

	@Override
	public List<Country> findAll() throws LogicException {
		try {
			List<Country> list = countryDao.read();
			list.forEach((x) -> {
				Currency currency=x.getCurrency();
				try {
					currency = currencyDao.read(currency.getId());
					x.setCurrency(currency);		
				} catch (DaoException e) {
					e.printStackTrace();}
				
						
			});
			return list;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public Long save(Country country) throws LogicException {
		try {
			Long id=country.getId();
			if(id == null) {
				id = countryDao.create(country);
				country.setId(id);
			} else {
				countryDao.update(country);
			}
			return id;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public void delete(Long id) throws LogicException {
		try {
			countryDao.delete(id);
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	@Override
	public Country read(Long id) throws LogicException {
		try {
			Country country=countryDao.read(id);
			Currency currency=currencyDao.read(country.getCurrency().getId());
			country.setCurrency(currency);
			return country;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	@Override
	public void delete(List<Long> ids) throws LogicException {
		try {
			for(Long id : ids) {
				countryDao.delete(id);
			}
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
}
