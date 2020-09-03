package org.itstep.daos;

import java.util.List;
import org.itstep.entities.Sale;
import org.itstep.postgres.DaoException;

public interface SaleDao extends Dao<Sale> {
	Long create(Sale sale) throws DaoException;
	Sale read(Long id) throws DaoException;
	void update(Sale sale) throws DaoException;
	void delete(Long id) throws DaoException;
	List<Sale> read() throws DaoException;
}