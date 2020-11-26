package org.itstep.de_services;

import java.util.List;

import org.itstep.entities.Currency;
import org.itstep.service.LogicException;

public interface CurrencyService extends BaseService<Currency> {
	List<Currency> findAll() throws LogicException;
	Long save(Currency currency) throws LogicException;
	void delete(Long id) throws LogicException;
	Currency findById(Long id) throws LogicException;
}
