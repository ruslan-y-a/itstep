package org.itstep.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.itstep.entities.User;

public interface UserRepository   extends CrudRepository<User, Long> {
	 @Query(value = "SELECT u FROM User u WHERE u.login = :login AND u.password = :password")
	    User authenticate(@Param("login") String login,@Param("password") String password);
  
}