package postgres;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tabs.Entity;

public interface DbDao extends Dao<Entity> {

	Long create(Entity entity) throws DaoException;
	Long create(String name,HashMap<String,Object> mapTab) throws DaoException;
	Long create(String name,ArrayList<String> fields, ArrayList<String> values) throws DaoException;
	Entity read(String name,long id) throws DaoException;
	List<Entity> read(String name) throws DaoException;
	Map<Long,Object> readField(String name, String tarfield, String field, Object fValue) throws DaoException;
	Map<Long,Object> readField(String name, String tarfield, String field, String expr) throws DaoException;
	List<Entity> read(String name, String field, Object fValue) throws DaoException;
	List<Entity> read(String name, String field, String expr) throws DaoException;
	List<Entity> read(String name, String[] field, Object[] fValue) throws DaoException;
	List<Entity> read(String name, String[] field, Number[] fValue, byte[] signs) throws DaoException;
	List<Entity> read(String name, String field, Object[] fValue) throws DaoException;
	Long[] readIDOr(String name, String field, Object[] fValue) throws DaoException;
	Long[] readIDAnd(String name, String field, Object[] fValue) throws DaoException;
	Long[] readID(String name, String field, Object fValue) throws DaoException;
	void update(Entity entity) throws DaoException;
	void update(String name, Long id, ArrayList<String> fields, ArrayList<String> values) throws DaoException;
	void update(String name, String field, Object fValue, String tfield, Object tValue) throws DaoException;
	void update(String name, String field[], Object fValue[], String tfield[], Object tValue[]) throws DaoException;
	void update(Entity entity, boolean chnull) throws DaoException;
	boolean delete(String name,Long id) throws DaoException;
	boolean delete(String name,  String field, Object fValue) throws DaoException;
	boolean delete(String name,  String[] field, Object[] fValue) throws DaoException;
}
