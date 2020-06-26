package de_services;

import java.util.List;

import entities.Currency;
import service.LogicException;

public interface CurrencyService extends BaseService<Currency> {
	List<Currency> findAll() throws LogicException;
	Long save(Currency currency) throws LogicException;
	void delete(Long id) throws LogicException;
	Currency read(Long id) throws LogicException;
}
