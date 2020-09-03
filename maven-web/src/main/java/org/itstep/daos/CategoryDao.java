package org.itstep.daos;

import java.util.List;

import org.itstep.entities.Category;
import org.itstep.postgres.DaoException;

public interface CategoryDao extends Dao<Category> {
	Long create(Category category) throws DaoException;
	Category read(Long id) throws DaoException;
	void update(Category category) throws DaoException;
	void delete(Long id) throws DaoException;
	List<Category> read() throws DaoException;
	Category read(String str) throws DaoException;
	List<Category> readByParent(Long parentid) throws DaoException;
}
