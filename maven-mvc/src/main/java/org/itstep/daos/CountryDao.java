package org.itstep.daos;

import java.util.List;
import org.itstep.entities.Country;

public interface CountryDao extends Dao<Country> {
	Long create(Country country) throws DaoException;
	Country read(Long id) throws DaoException;
	void update(Country country) throws DaoException;
	void delete(Long id) throws DaoException;
	List<Country> read() throws DaoException;
}