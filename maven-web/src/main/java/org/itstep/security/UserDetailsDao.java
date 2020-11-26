package org.itstep.security;

import org.itstep.entities.User;

public interface UserDetailsDao {
	  User findUserByLogin(String login);
	  void saveUser(User user);
}
