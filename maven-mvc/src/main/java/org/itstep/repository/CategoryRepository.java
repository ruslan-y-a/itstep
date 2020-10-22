package org.itstep.repository;

import java.util.List;

import org.itstep.entities.Category;
//import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

public interface CategoryRepository  extends CrudRepository <Category, Long>  {
	 @Query(value = "SELECT c FROM Category c WHERE c.name = :keyword")
	    Category search(@Param("keyword") String keyword);
	 
	 @Query(value = "SELECT c FROM Category c WHERE c.name LIKE '%' || :keyword || '%'")
	    List<Category> searchLike(@Param("keyword") String keyword);
	 
	 @Query(value = "SELECT c FROM Category c WHERE c.parentid= :pid")
	    List<Category> readByParent(@Param("pid") Long pid);

}
