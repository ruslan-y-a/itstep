package daos;

import postgres.DaoException;

import java.util.List;
import entities.Webpages;
 
public interface WebpagesDao  extends Dao<Webpages> {
	Long create(Webpages webpages) throws DaoException;
	Webpages read(Long id) throws DaoException;
	void update(Webpages webpages) throws DaoException;
	void delete(Long id) throws DaoException;
	List<Webpages> read() throws DaoException;
}