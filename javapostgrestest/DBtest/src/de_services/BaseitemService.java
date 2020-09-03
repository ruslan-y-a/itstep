package de_services;

import java.util.List;

import entities.Baseitem;
import service.LogicException;

public interface BaseitemService extends BaseService<Baseitem> {
	List<Baseitem> findAll() throws LogicException;
	Long save(Baseitem baseitem) throws LogicException;
	void delete(Long id) throws LogicException;
	Baseitem read(Long id) throws LogicException;
	List<Baseitem> readItemRow(Long itemid) throws LogicException;
}