package org.itstep.de_services;

import java.util.List;

import org.itstep.entities.Baseitem;
import org.itstep.service.LogicException;

public interface BaseitemService extends BaseService<Baseitem> {
	List<Baseitem> findAll() throws LogicException;
	Long save(Baseitem baseitem) throws LogicException;
	void delete(Long id) throws LogicException;
	Baseitem findById(Long id) throws LogicException;
	List<Baseitem> readItemRow(Long itemid) throws LogicException;
	public Baseitem readByICS(Long id,Long colorid,Long sizeid) throws LogicException;
}