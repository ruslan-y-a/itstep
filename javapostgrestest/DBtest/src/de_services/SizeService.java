package de_services;

import java.util.List;

import entities.Size;
import service.LogicException;

public interface SizeService extends BaseService<Size> {
	List<Size> findAll() throws LogicException;
	Long save(Size variant) throws LogicException;
	void delete(Long id) throws LogicException;
	Size read(Long id) throws LogicException;
}
