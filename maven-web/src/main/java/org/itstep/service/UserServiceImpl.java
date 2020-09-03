package org.itstep.service;

import org.itstep.tabs.User;
import org.itstep.postgres.DaoException;
import org.itstep.postgres.UserDao;

public class UserServiceImpl implements UserService {
	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;}

	@Override
	public User authenticate(String login, String password) throws LogicException {
		try {
			return userDao.read(login, password);
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
}
