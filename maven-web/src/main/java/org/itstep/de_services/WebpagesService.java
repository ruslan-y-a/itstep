package org.itstep.de_services;

import java.util.List;

import org.itstep.entities.Webpages;
import org.itstep.service.LogicException;

public interface WebpagesService extends BaseService<Webpages> {
	List<Webpages> findAll() throws LogicException;
	Long save(Webpages webpages) throws LogicException;
	void delete(Long id) throws LogicException;
	Webpages findById(Long id) throws LogicException;
	Webpages readEntityId(String uri) throws LogicException;
	Webpages findByEntityId(Long eid, String entity) throws LogicException;
}
