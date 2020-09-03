package org.itstep.de_services;

import java.util.List;

import org.itstep.entities.Sale;
import org.itstep.service.LogicException;

public interface SaleService extends BaseService<Sale> {
	List<Sale> findAll() throws LogicException;
	Long save(Sale sale) throws LogicException;
	void delete(Long id) throws LogicException;
	Sale read(Long id) throws LogicException;
}
