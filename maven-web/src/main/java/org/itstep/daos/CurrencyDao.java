package org.itstep.daos;

import java.util.List;
import org.itstep.entities.Currency;
import org.itstep.postgres.DaoException;

public interface CurrencyDao extends Dao<Currency> {
	Long create(Currency currency) throws DaoException;
	Currency read(Long id) throws DaoException;
	void update(Currency currency) throws DaoException;
	void delete(Long id) throws DaoException;
	List<Currency> read() throws DaoException;
}