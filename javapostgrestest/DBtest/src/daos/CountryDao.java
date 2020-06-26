package daos;

import java.util.List;
import entities.Country;
import postgres.DaoException;

public interface CountryDao extends Dao<Country> {
	Long create(Country country) throws DaoException;
	Country read(Long id) throws DaoException;
	void update(Country country) throws DaoException;
	void delete(Long id) throws DaoException;
	List<Country> read() throws DaoException;
}