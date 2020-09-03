package org.itstep.daos;

import java.util.List;
import org.itstep.entities.Variant;
import org.itstep.postgres.DaoException;

public interface VariantDao extends Dao<Variant> {
	Long create(Variant variant) throws DaoException;	
	Variant read(Long id) throws DaoException;
	void update(Variant variant) throws DaoException;
	void delete(Long id) throws DaoException;
	List<Variant> read() throws DaoException;
} 
