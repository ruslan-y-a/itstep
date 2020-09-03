package org.itstep.de_services;

import java.util.List;

import org.itstep.entities.User;
import org.itstep.service.LogicException;

public interface UserService extends BaseService<User> {
	List<User> findAll() throws LogicException;
	Long save(User user) throws LogicException;
	void delete(Long id) throws LogicException;
	User read(Long id) throws LogicException;
	User authenticate(String login, String password) throws LogicException;
}
