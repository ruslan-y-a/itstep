package org.itstep.postgres;

import org.itstep.tabs.User;

public interface UserDao extends Dao<User> {
	User read(String login, String password) throws DaoException;
	User read(Long id) throws DaoException;
	void update(User user) throws DaoException;
	void delete(Long id) throws DaoException;
}
