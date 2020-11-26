package org.itstep.repository;

import java.util.List;

import org.itstep.entities.Orders;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface OrdersRepository { /*extends CrudRepository<Orders, Long> {	
	 
	 @Query(value = "SELECT u FROM Orders u WHERE u.status = 1 AND u.client = :client AND u.active = true")
	 List<Orders> orderdItems(@Param("client") Long client);
	 
	 @Query(value = "SELECT u FROM Orders u WHERE u.status = :status AND u.client = :client AND u.active = true")
	 List<Orders> search(@Param("status") Integer status, @Param("client") Long client);
	 
	 @Query(value = "SELECT u FROM Orders u WHERE u.client = :client AND u.active = true")
	 List<Orders> clientOrderedItems( @Param("client") Long client);
	
	@Query(value = "SELECT u.number FROM Orders u ORDER BY u.number DESC LIMIT 1")
	Integer getMaxOrder();
	 	 	 */
}
