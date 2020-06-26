package daos;

import postgres.DaoException;

import java.util.List;

import entities.Baseitem;

public interface BaseitemDao extends Dao<Baseitem> {
	Long create(Baseitem baseitem) throws DaoException;
	Baseitem read(Long id) throws DaoException;
	void update(Baseitem baseitem) throws DaoException;
	void delete(Long id) throws DaoException;
	List<Baseitem> read() throws DaoException;
}