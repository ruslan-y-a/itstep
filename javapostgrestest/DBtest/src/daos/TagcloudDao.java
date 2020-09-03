package daos;

import java.util.List;

import entities.Tagcloud;
import postgres.DaoException;

public interface TagcloudDao extends Dao<Tagcloud> {
	Long create(Tagcloud tagcloud) throws DaoException;
	Tagcloud read(Long id) throws DaoException;
	void update(Tagcloud tagcloud) throws DaoException;
	void delete(Long id) throws DaoException;
	List<Tagcloud> read() throws DaoException;
	List<Long> readByWP(Long id) throws DaoException;
}