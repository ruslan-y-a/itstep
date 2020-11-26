package org.itstep.daos;

import java.util.List;

import org.itstep.entities.Color;

public interface ColorDao extends Dao<Color> {
	Long create(Color color) throws DaoException;	
	Color read(Long id) throws DaoException;
	void update(Color color) throws DaoException;
	void delete(Long id) throws DaoException;
	List<Color> read() throws DaoException;
} 
