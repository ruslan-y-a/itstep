package sqlSetGet;

import java.sql.PreparedStatement;
import postgres.DaoException;
public interface SqlSetter {
	void sqlSet(PreparedStatement s,Integer i,Object o) throws DaoException;
	void sqlSet(PreparedStatement s, Integer i, String ss) throws DaoException;
}