package postgres;

import java.util.List;
import tabs.Entity;

public interface DbDao extends Dao<Entity> {

	Long create(Entity entity) throws DaoException;
	Entity read(String name,long id) throws DaoException;
	List<Entity> read(String name) throws DaoException;
	void update(Entity entity) throws DaoException;
	boolean delete(String name,Long id) throws DaoException;
	
}
