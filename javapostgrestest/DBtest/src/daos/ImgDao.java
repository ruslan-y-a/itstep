package daos;

import java.util.List;

import entities.Img;
import postgres.DaoException;

public interface ImgDao extends Dao<Img> {
	Long create(Img img) throws DaoException;
	Img read(Long id) throws DaoException;
	void update(Img img) throws DaoException;
	void delete(Long id) throws DaoException;
	List<Img> read() throws DaoException;
}