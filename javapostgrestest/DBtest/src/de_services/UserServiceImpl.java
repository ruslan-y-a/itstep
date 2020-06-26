package de_services;

import java.util.List;

import daos.UserDao;
import entities.User;
import postgres.DaoException;
import service.LogicException;

public class UserServiceImpl implements UserService {

	private UserDao userDao;

	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public List<User> findAll() throws LogicException {
		try {					
			return  userDao.read();
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public Long save(User user) throws LogicException {
		try {
			Long id =user.getId();
			if(id == null) {
				 userDao.create(user);
				user.setId(id);
			} else {
				userDao.update(user);
			}
			return id;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public void delete(Long id) throws LogicException {
		try {
			userDao.delete(id);
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	@Override
	public User read(Long id) throws LogicException {
		try {					
			return userDao.read(id);
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	@Override
	public User authenticate(String login, String password) throws LogicException {
	//	 System.out.println("authenticate" + login + " " + password);
		try {
			User user=userDao.read(login, password);
			user=userDao.read(user.getId());
			return user;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	@Override
	public void delete(List<Long> ids) throws LogicException {
		try {
			for(Long id : ids) {
				userDao.delete(id);
			}
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
}
