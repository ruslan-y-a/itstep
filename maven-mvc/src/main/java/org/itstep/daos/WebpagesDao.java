package org.itstep.daos;


import java.util.List;
import org.itstep.entities.Webpages;
 
public interface WebpagesDao  extends Dao<Webpages> {
	Long create(Webpages webpages) throws DaoException;
	Webpages read(Long id) throws DaoException;
	void update(Webpages webpages) throws DaoException;
	void delete(Long id) throws DaoException;
	List<Webpages> read() throws DaoException;
	Webpages readEntityId(String uri) throws DaoException;
	Webpages fingByEntityId(Long eid, String entity) throws DaoException;
}