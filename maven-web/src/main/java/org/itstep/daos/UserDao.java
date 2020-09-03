package org.itstep.daos;

import org.itstep.postgres.DaoException;

import java.util.List;
import org.itstep.entities.User;

public interface UserDao extends Dao<User> {
	Long create(User user) throws DaoException;
	User read(String login, String password) throws DaoException;
	User read(Long id) throws DaoException;
	void update(User user) throws DaoException;
	void delete(Long id) throws DaoException;
	List<User> read() throws DaoException;
}
