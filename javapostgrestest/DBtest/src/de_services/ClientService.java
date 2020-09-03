package de_services;

import java.util.List;

import entities.Client;
import service.LogicException;

public interface ClientService extends BaseService<Client> {
	List<Client> findAll() throws LogicException;
	Long save(Client category) throws LogicException;
	void delete(Long id) throws LogicException;
	Client read(Long id) throws LogicException;
	Client readByUserId(Long id) throws LogicException;
	Long readByUserId(Long id, boolean bJustId) throws LogicException;
}
