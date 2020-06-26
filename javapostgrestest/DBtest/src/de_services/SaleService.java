package de_services;

import java.util.List;

import entities.Sale;
import service.LogicException;

public interface SaleService extends BaseService<Sale> {
	List<Sale> findAll() throws LogicException;
	Long save(Sale sale) throws LogicException;
	void delete(Long id) throws LogicException;
	Sale read(Long id) throws LogicException;
}
