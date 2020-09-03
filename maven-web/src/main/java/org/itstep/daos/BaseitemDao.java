package org.itstep.daos;

import org.itstep.postgres.DaoException;

import java.util.List;

import org.itstep.entities.Baseitem;

public interface BaseitemDao extends Dao<Baseitem> {
	Long create(Baseitem baseitem) throws DaoException;
	Baseitem read(Long id) throws DaoException;
	Baseitem read(Long id, boolean bDisk) throws DaoException;
	void update(Baseitem baseitem) throws DaoException;
	void delete(Long id) throws DaoException;
	List<Baseitem> read() throws DaoException;
	List<Baseitem> readItemRow(Long itemid) throws DaoException;
	Boolean getInStock(Long itemid) throws DaoException;
}