package daos;

import java.util.List;

import entities.Category;
import postgres.DaoException;

public interface CategoryDao extends Dao<Category> {
	Long create(Category category) throws DaoException;
	Category read(Long id) throws DaoException;
	void update(Category category) throws DaoException;
	void delete(Long id) throws DaoException;
	List<Category> read() throws DaoException;
}
