package org.itstep.repository;
/*
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
*/
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.itstep.entities.Currency;

public interface CurrencyRepository extends CrudRepository <Currency, Long> {
/*
	  @Query(value = "SELECT u FROM Currency u WHERE u.name LIKE '%' || :keyword || '%'")
	    List<Currency> search(@Param("keyword") String keyword);
	    */ 	 
}