package org.itstep.daos;

import java.util.List;
import org.itstep.entities.Entity;
import org.itstep.postgres.DaoException;

public interface Dao<T extends Entity> {
	Long create(T entity) throws DaoException;
	T read(Long id) throws DaoException;
	void update(T t) throws DaoException;
	void delete(Long id) throws DaoException;
	List<T> read() throws DaoException;
}