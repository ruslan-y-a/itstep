package de_services;

import java.util.List;

import entities.Category;
import service.LogicException;

public interface CategoryService extends BaseService<Category> {
	List<Category> findAll() throws LogicException;
	Long save(Category category) throws LogicException;
	void delete(Long id) throws LogicException;
	Category read(Long id) throws LogicException;
}
