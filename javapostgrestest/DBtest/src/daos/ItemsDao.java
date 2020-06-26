package daos;

import java.util.List;
import entities.Items;
import postgres.DaoException;

public interface ItemsDao extends Dao<Items> {
	Long create(Items items) throws DaoException;
	Items read(Long id) throws DaoException;
	void update(Items items) throws DaoException;
	void delete(Long id) throws DaoException;
	List<Items> read() throws DaoException;
	List<Items> search(String search) throws DaoException;
}
