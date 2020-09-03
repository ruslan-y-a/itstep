package org.itstep.de_services;

import java.util.List;
import org.itstep.entities.Entity;
import org.itstep.service.LogicException;

public interface BaseService<T extends Entity> {
	List<T> findAll() throws LogicException;
	Long save(T t) throws LogicException;
	void delete(Long id) throws LogicException;
	T read(Long id) throws LogicException;	
	void delete(List<Long> ids) throws LogicException;
}
