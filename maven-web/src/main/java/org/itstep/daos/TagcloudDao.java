package org.itstep.daos;

import java.util.List;

import org.itstep.entities.Tagcloud;

public interface TagcloudDao extends Dao<Tagcloud> {
	Long create(Tagcloud tagcloud) throws DaoException;
	Tagcloud read(Long id) throws DaoException;
	void update(Tagcloud tagcloud) throws DaoException;
	void delete(Long id) throws DaoException;
	List<Tagcloud> read() throws DaoException;
	List<Long> readByWP(Long id) throws DaoException;
}