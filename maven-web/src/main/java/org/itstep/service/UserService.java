package org.itstep.service;

import org.itstep.tabs.User;

public interface UserService {
	User authenticate(String login, String password) throws LogicException;
}
