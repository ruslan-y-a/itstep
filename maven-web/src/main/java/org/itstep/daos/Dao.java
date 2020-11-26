package org.itstep.daos;

import java.util.List;

public interface Dao<T> {
	Long create(T entity) throws DaoException;
	T read(Long id) throws DaoException;
	void update(T t) throws DaoException;
	void delete(Long id) throws DaoException;
	List<T> read() throws DaoException;
}