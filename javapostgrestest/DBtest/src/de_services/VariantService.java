package de_services;

import java.util.List;

import entities.Variant;
import service.LogicException;

public interface VariantService extends BaseService<Variant> {
	List<Variant> findAll() throws LogicException;
	Long save(Variant variant) throws LogicException;
	void delete(Long id) throws LogicException;
	Variant read(Long id) throws LogicException;
}
