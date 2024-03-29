package daos;

import java.util.List;

import entities.Size;
import postgres.DaoException;

public interface SizeDao extends Dao<Size> {
	Long create(Size Size) throws DaoException;	
	Size read(Long id) throws DaoException;
	void update(Size Size) throws DaoException;
	void delete(Long id) throws DaoException;
	List<Size> read() throws DaoException;
} 
