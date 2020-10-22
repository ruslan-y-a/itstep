package org.itstep.daos;

import java.util.List;

import org.itstep.entities.Classification;

public interface ClassificationDao extends Dao<Classification> {
	Long create(Classification classification) throws DaoException;
	Classification read(Long id) throws DaoException;
	void update(Classification classification) throws DaoException;
	void delete(Long id) throws DaoException;
	List<Classification> read() throws DaoException;
	Classification readByCategory(Long id) throws DaoException;
}
