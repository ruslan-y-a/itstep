package org.itstep.repository;

import org.itstep.entities.Img;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public interface ImgRepository  extends CrudRepository<Img, Long>{
	   @Query(value = "SELECT c.id FROM Img c WHERE c.url = :surl")
	    Img findByUrl(@Param("surl") String surl);
}
