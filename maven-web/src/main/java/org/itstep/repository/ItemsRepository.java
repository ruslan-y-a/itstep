package org.itstep.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.itstep.entities.Items;

public interface ItemsRepository { // extends CrudRepository <Items, Long> {
	 /*
    @Query(value = "SELECT u FROM Items u WHERE u.name LIKE '%' || :keyword || '%' OR u.articul LIKE '%' || :keyword || '%'")
    List<Items> search(@Param("keyword") String keyword); 	 
   @Query(value = "SELECT c.id FROM Client c INNER JOIN User u ON c.userid = u.id WHERE u.id = :uid")
    Long findByUserId(@Param("uid") Long uid);
    @Query(value = "SELECT c.countryid, c.address, c.creationdate, c.userid, c.bonuspoints, c.phoneno, c.recentitems, u.name FROM Client c INNER JOIN User u ON c.userid = u.id WHERE u.id = :uid")
    Client readByUserId(@Param("uid") Long uid);*/
}

