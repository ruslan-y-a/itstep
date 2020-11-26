package org.itstep.de_services;

import java.util.List;

import org.itstep.entities.Tagcloud;
import org.itstep.service.LogicException;

public interface TagcloudService extends BaseService<Tagcloud> {
	List<Tagcloud> findAll() throws LogicException;
	Long save(Tagcloud tagcloud) throws LogicException;
	void delete(Long id) throws LogicException;
	Tagcloud findById(Long id) throws LogicException;
	List<Long> readByWP(Long id) throws LogicException;
}
