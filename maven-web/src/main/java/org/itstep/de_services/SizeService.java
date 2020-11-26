package org.itstep.de_services;

import java.util.List;

import org.itstep.entities.Size;
import org.itstep.service.LogicException;

public interface SizeService extends BaseService<Size> {
	List<Size> findAll() throws LogicException;
	Long save(Size variant) throws LogicException;
	void delete(Long id) throws LogicException;
	Size findById(Long id) throws LogicException;
}
