package de_services;

import java.util.List;

import entities.Tagcloud;
import service.LogicException;

public interface TagcloudService extends BaseService<Tagcloud> {
	List<Tagcloud> findAll() throws LogicException;
	Long save(Tagcloud tagcloud) throws LogicException;
	void delete(Long id) throws LogicException;
	Tagcloud read(Long id) throws LogicException;
	List<Long> readByWP(Long id) throws LogicException;
}
