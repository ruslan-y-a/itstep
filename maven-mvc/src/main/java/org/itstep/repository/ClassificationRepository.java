package org.itstep.repository;

import java.util.List;

import org.itstep.entities.Classification;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;

public interface ClassificationRepository extends CrudRepository <Classification, Long> {	
	 @Query(value = "SELECT c FROM Classification c WHERE c.name LIKE '%' || :keyword || '%'")
	    List<Classification> search(@Param("keyword") String keyword);
	 
	 @Query(value = "SELECT c FROM Classification c WHERE c.categoryid= :cid")
	 public Classification readByCategory(@Param("cid") Long cid);	    
}
