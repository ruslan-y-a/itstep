package de_services;

import java.util.List;

import entities.Webpages;
import service.LogicException;

public interface WebpagesService extends BaseService<Webpages> {
	List<Webpages> findAll() throws LogicException;
	Long save(Webpages webpages) throws LogicException;
	void delete(Long id) throws LogicException;
	Webpages read(Long id) throws LogicException;
}
