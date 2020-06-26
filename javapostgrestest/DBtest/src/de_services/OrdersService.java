package de_services;

import java.util.List;

import entities.Orders;
import service.LogicException;

public interface OrdersService extends BaseService<Orders> {
	List<Orders> findAll() throws LogicException;
	Long save(Orders orders) throws LogicException;
	void delete(Long id) throws LogicException;
	Orders read(Long id) throws LogicException;
}
