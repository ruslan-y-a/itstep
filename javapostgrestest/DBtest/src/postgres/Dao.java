package postgres;

import java.util.List;

import tabs.Entity;

public interface Dao<T extends Entity> {
	Long create(T entity) throws DaoException;
	T read(String name,long id) throws DaoException;
	List<T> read(String name) throws DaoException;
	List<T> read(String name, String field, Object fValue) throws DaoException;
	List<T> read(String name, String[] field, Object[] fValue) throws DaoException;
	List<T> read(String name, String[] field, Number[] fValue, byte[] signs) throws DaoException;
	List<T> read(String name, String field, Object[] fValue) throws DaoException;
	Long[] readIDOr(String name, String field, Object[] fValue) throws DaoException;
	Long[] readIDAnd(String name, String field, Object[] fValue) throws DaoException;
	Long[] readID(String name, String field, Object fValue) throws DaoException;
	void update(T entity) throws DaoException;	
	void update(String name, String field, Object fValue, String tfield, Object tValue) throws DaoException;
	void update(String name, String field[], Object fValue[], String tfield[], Object tValue[]) throws DaoException;
	void update(T entity, boolean chnull) throws DaoException;
	boolean delete(String name,Long id) throws DaoException;
	boolean delete(String name,  String field, Object fValue) throws DaoException;
	boolean delete(String name,  String[] field, Object[] fValue) throws DaoException;
}