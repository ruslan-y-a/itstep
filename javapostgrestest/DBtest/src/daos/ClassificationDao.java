package daos;

import java.util.List;

import entities.Classification;
import postgres.DaoException;

public interface ClassificationDao extends Dao<Classification> {
	Long create(Classification classification) throws DaoException;
	Classification read(Long id) throws DaoException;
	void update(Classification classification) throws DaoException;
	void delete(Long id) throws DaoException;
	List<Classification> read() throws DaoException;
}
