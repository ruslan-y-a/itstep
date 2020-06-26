package de_services;

import java.util.List;

import entities.Items;
import service.LogicException;

public interface ItemsService extends BaseService<Items> {
	List<Items> findAll() throws LogicException;
	Long save(Items items) throws LogicException;
	void delete(Long id) throws LogicException;
	Items read(Long id) throws LogicException;
	List<Items> search(String search) throws LogicException;
}
