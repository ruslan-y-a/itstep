package de_services;

import java.util.List;
import entities.Classification;
import service.LogicException;

public interface ClassificationService extends BaseService<Classification> {
	List<Classification> findAll() throws LogicException;
	Long save(Classification classification) throws LogicException;
	void delete(Long id) throws LogicException;
	Classification read(Long id) throws LogicException;
	List<Classification> readByCategory(List<Long> id) throws LogicException;
}
