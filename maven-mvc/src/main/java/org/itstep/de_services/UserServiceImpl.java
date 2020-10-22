package org.itstep.de_services;

import java.util.List;

import org.itstep.daos.UserDao;
import org.itstep.entities.User;
import org.itstep.daos.DaoException;
import org.itstep.service.LogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component
@Scope("prototype")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDao userDao;
	public void setUserDao(UserDao userDao) {this.userDao = userDao;}

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
				id=userDao.create(user);
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
	public User findById(Long id) throws LogicException {
		try {					
			return userDao.read(id);
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	@Override
	public User authenticate(String login, String password) throws LogicException {
	//	 System.out.println("=====================authenticate)" + login + " " + password);
		try {
			User user=userDao.read(login, password);
			if (user!=null) {user=userDao.read(user.getId());}
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
