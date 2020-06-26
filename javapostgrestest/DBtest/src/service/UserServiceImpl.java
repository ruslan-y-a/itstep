package service;

import tabs.User;
import postgres.DaoException;
import postgres.UserDao;

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
