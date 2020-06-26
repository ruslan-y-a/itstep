package service;

import tabs.User;

public interface UserService {
	User authenticate(String login, String password) throws LogicException;
}
