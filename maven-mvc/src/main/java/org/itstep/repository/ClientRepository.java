package org.itstep.repository;

//import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.itstep.entities.Client;

public interface ClientRepository { /* extends CrudRepository <Client, Long> {

	    @Query(value = "SELECT c.id FROM Client c INNER JOIN User u ON c.user.id = u.id WHERE u.id = :uid")
	    Long findByUserId(@Param("uid") Long uid);
	    @Query(value = "SELECT c.country, c.address, c.creationdate, c.user, c.bonuspoints, c.phoneno, u.name FROM Client c INNER JOIN User u ON c.user.id = u.id WHERE u.id = :uid") //c.recentitems,
	    Client readByUserId(@Param("uid") Long uid);
	    */
}

