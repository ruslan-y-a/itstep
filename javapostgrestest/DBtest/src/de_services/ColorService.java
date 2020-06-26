package de_services;

import java.util.List;

import entities.Color;
import service.LogicException;

public interface ColorService extends BaseService<Color> {
	List<Color> findAll() throws LogicException;
	Long save(Color variant) throws LogicException;
	void delete(Long id) throws LogicException;
	Color read(Long id) throws LogicException;
}
