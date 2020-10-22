package org.itstep.csvupdater.sqlSetGet;
import org.itstep.daos.DaoException;
import java.sql.PreparedStatement;

public interface SqlSetter {
	void sqlSet(PreparedStatement s,Integer i,Object o) throws DaoException;
	void sqlSet(PreparedStatement s, Integer i, String ss) throws DaoException;
}