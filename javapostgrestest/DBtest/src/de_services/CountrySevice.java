package de_services;

import java.util.List;

import entities.Country;
import service.LogicException;

public interface CountrySevice extends BaseService<Country> {
	List<Country> findAll() throws LogicException;
	Long save(Country country) throws LogicException;
	void delete(Long id) throws LogicException;
	Country read(Long id) throws LogicException;
}
