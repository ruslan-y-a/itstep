package org.itstep.de_services;

import java.util.List;

import org.itstep.entities.Color;
import org.itstep.service.LogicException;

public interface ColorService extends BaseService<Color> {
	List<Color> findAll() throws LogicException;
	Long save(Color variant) throws LogicException;
	void delete(Long id) throws LogicException;
	Color read(Long id) throws LogicException;
}
