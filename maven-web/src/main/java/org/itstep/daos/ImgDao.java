package org.itstep.daos;

import java.util.List;

import org.itstep.entities.Img;
import org.itstep.postgres.DaoException;

public interface ImgDao extends Dao<Img> {
	Long create(Img img) throws DaoException;
	Img read(Long id) throws DaoException;
	void update(Img img) throws DaoException;
	void delete(Long id) throws DaoException;
	List<Img> read() throws DaoException;
}