package org.itstep.repository;

import java.util.List;

import org.itstep.entities.Webpages;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

public interface WebpagesRepository   extends CrudRepository<Webpages, Long> {
	 @Query(value = "SELECT c FROM Webpages c WHERE c.url =:url")
	 Webpages readEntityId(@Param("url") String url);

	 @Query(value = "SELECT c FROM Webpages c WHERE c.entityid= :eid AND c.entity = :sentity")
	 Webpages fingByEntityId(@Param("eid") Long eid,@Param("sentity") String sentity);
}
