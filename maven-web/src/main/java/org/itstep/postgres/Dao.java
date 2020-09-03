package org.itstep.postgres;
/*
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
*/
import org.itstep.tabs.Entity;

public interface Dao<T extends Entity> {
	Long create(T entity) throws DaoException;
/*	Long create(String name,HashMap<String,Object> mapTab) throws DaoException;
	Long create(String name,ArrayList<String> fields, ArrayList<String> values) throws DaoException;
	T read(String name,long id) throws DaoException;
	List<T> read(String name) throws DaoException;
	Map<Long,Object> readField(String name, String tarfield, String field, Object fValue) throws DaoException;
	Map<Long,Object> readField(String name, String tarfield, String field, String expr) throws DaoException;
	List<T> read(String name, String field, Object fValue) throws DaoException;
	List<T> read(String name, String field, String expr) throws DaoException;
	List<T> read(String name, String[] field, Object[] fValue) throws DaoException;
	List<T> read(String name, String[] field, Number[] fValue, byte[] signs) throws DaoException;
	List<T> read(String name, String field, Object[] fValue) throws DaoException;
	Long[] readIDOr(String name, String field, Object[] fValue) throws DaoException;
	Long[] readIDAnd(String name, String field, Object[] fValue) throws DaoException;
	Long[] readID(String name, String field, Object fValue) throws DaoException;
	void update(T entity) throws DaoException;	
	void update(String name, Long id, ArrayList<String> fields, ArrayList<String> values) throws DaoException;
	void update(String name, String field, Object fValue, String tfield, Object tValue) throws DaoException;
	void update(String name, String field[], Object fValue[], String tfield[], Object tValue[]) throws DaoException;
	void update(T entity, boolean chnull) throws DaoException;
	boolean delete(String name,Long id) throws DaoException;
	boolean delete(String name,  String field, Object fValue) throws DaoException;
	boolean delete(String name,  String[] field, Object[] fValue) throws DaoException;*/
}