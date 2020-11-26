package org.itstep.de_services;

import java.util.List;
//import org.itstep.entities.BaseEntity;
import org.itstep.service.LogicException;

public interface BaseService<T> {
	List<T> findAll() throws LogicException;
	Long save(T t) throws LogicException;
	void delete(Long id) throws LogicException;
	T findById(Long id) throws LogicException;	
	void delete(List<Long> ids) throws LogicException;
}
