package org.itstep.de_services;

import java.util.List;

import org.itstep.entities.Category;
import org.itstep.service.LogicException;

public interface CategoryService extends BaseService<Category> {
	List<Category> findAll() throws LogicException;
	Long save(Category category) throws LogicException;
	void delete(Long id) throws LogicException;
	Category read(Long id) throws LogicException;
	Category read(String str) throws LogicException;
	List<Category> readByParent(Long parentid) throws LogicException;
}
