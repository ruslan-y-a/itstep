package org.itstep.de_services;

import java.util.List;

import org.itstep.daos.CurrencyDao;
import org.itstep.daos.CountryDao;
import org.itstep.entities.Country;
import org.itstep.entities.Currency;
import org.itstep.daos.DaoException;
import org.itstep.service.LogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class CountrySeviceImpl implements CountrySevice {
	@Autowired	
	private CurrencyDao currencyDao;
	@Autowired	
	private CountryDao countryDao;

	public void setCurrencyDao(CurrencyDao currencyDao) {this.currencyDao = currencyDao;}
	public void setCountryDao(CountryDao countryDao) {this.countryDao= countryDao;}

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
	public Country findById(Long id) throws LogicException {
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
