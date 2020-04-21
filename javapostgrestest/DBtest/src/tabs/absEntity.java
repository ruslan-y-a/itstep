package tabs;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import postgres.DaoException;

public interface absEntity {
	public void setToTab (PreparedStatement s, Integer i,String columnName) throws DaoException;
	public void getFromTab (ResultSet r, String columnName) throws DaoException;
	public void setForSelect (PreparedStatement s, Integer i,String columnName,Object o) throws DaoException;
	public void cast();
	public void cast(String name);
}
