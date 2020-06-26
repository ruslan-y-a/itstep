package de_services;

import java.util.List;

import entities.User;
import service.LogicException;

public interface UserService extends BaseService<User> {
	List<User> findAll() throws LogicException;
	Long save(User user) throws LogicException;
	void delete(Long id) throws LogicException;
	User read(Long id) throws LogicException;
	User authenticate(String login, String password) throws LogicException;
}
