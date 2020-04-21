package postgres;

import tabs.Entity;

public interface Dao<T extends Entity> {
	Long create(T entity) throws DaoException;
	T read(String name,long id) throws DaoException;
	void update(T entity) throws DaoException;	
	boolean delete(String name,Long id) throws DaoException;
}