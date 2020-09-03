package org.itstep.de_services;

import java.util.List;

import org.itstep.entities.Img;
import org.itstep.service.LogicException;

public interface ImgService extends BaseService<Img> {
	List<Img> findAll() throws LogicException;
	Long save(Img img) throws LogicException;
	void delete(Long id) throws LogicException;
	Img read(Long id) throws LogicException;
}
