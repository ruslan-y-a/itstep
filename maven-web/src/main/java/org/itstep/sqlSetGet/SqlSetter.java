package org.itstep.sqlSetGet;

import java.sql.PreparedStatement;
import org.itstep.postgres.DaoException;
public interface SqlSetter {
	void sqlSet(PreparedStatement s,Integer i,Object o) throws DaoException;
	void sqlSet(PreparedStatement s, Integer i, String ss) throws DaoException;
}