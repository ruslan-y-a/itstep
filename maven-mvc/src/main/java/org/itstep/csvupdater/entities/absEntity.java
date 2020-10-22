package org.itstep.csvupdater.entities;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.itstep.daos.DaoException;

public interface absEntity {	
	public void setToTab (PreparedStatement s, Integer i,String columnName) throws DaoException;
	void setToTab (PreparedStatement s, Integer i,String columnName, Object o) throws DaoException;
	public void setToTab (PreparedStatement s, Integer i,String columnName,String value) throws DaoException;
	void getNameFromTab (ResultSet r, String columnName) throws DaoException;
	Object getObjectFromTab (ResultSet r, String columnName) throws DaoException;
	public void setForSelect (PreparedStatement s, Integer i,String columnName,Object o) throws DaoException;
	void setForSelect (PreparedStatement s, Integer i,String columnName, String o) throws DaoException;
	public void cast();
	public void cast(String name);
}
