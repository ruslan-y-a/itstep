package de_services;

import java.util.ArrayList;
import java.util.List;

import daos.ClientDao;
import daos.CountryDao;
import daos.ItemsDao;
import daos.UserDao;
import entities.Client;
import entities.Country;
import entities.Items;
import entities.User;
import postgres.DaoException;
import service.LogicException;

public class ClientServiceImpl implements ClientService {
//	   List<Items>
	private ClientDao clientDao;
	private CountryDao countryDao;
	private UserDao userDao;
	private ItemsDao itemsDao;
	
	public void setClientDao(ClientDao clientDao) {
		this.clientDao = clientDao;}
	public void setCountryDao(CountryDao countryDao) {
		this.countryDao= countryDao;}
	public void setUserDao(UserDao userDao) {
		this.userDao= userDao;}
	public void setItemsDao(ItemsDao itemsDao) {
		this.itemsDao= itemsDao;}

	@Override
	public List<Client> findAll() throws LogicException {
		try {
			List<Client> list = clientDao.read();
			list.forEach((x) -> {				
				try {
					Country country =countryDao.read(x.getCountry().getId());
					x.setCountry(country);
					User user = userDao.read(x.getUser().getId());
					x.setUser(user);
					List<Items> ilist=new ArrayList<>();
					x.getRecentitems().forEach((y)->{
					//	System.out.println("-----------" + y);
						  try {
							ilist.add(itemsDao.read(y.getId()));
						  } catch (DaoException e) {					
							e.printStackTrace();}
					}); 
					x.setRecentitems(ilist);
					
				} catch (DaoException e) {
					e.printStackTrace();}
				
						
			});
			return list;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public Long save(Client client) throws LogicException {
		try {
			Long id=client.getId();
			if( id== null) {
				id = clientDao.create(client);
				client.setId(id);
			} else {
				clientDao.update(client);
			}
			return id;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public void delete(Long id) throws LogicException {
		try {
			clientDao.delete(id);
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	@Override
	public Client read(Long id) throws LogicException {
		try {
			Client client=clientDao.read(id);
			Country country =countryDao.read(client.getCountry().getId());
			client.setCountry(country);
			User user = userDao.read(client.getUser().getId());
			client.setUser(user);
			List<Items> ilist=new ArrayList<>();
			client.getRecentitems().forEach((y)->{
				  try {
					ilist.add(itemsDao.read(y.getId()));
				  } catch (DaoException e) {					
					e.printStackTrace();}
			}); 
			client.setRecentitems(ilist);
						
			return client;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	@Override
	public void delete(List<Long> ids) throws LogicException {
		try {
			for(Long id : ids) {
				clientDao.delete(id);
			}
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
///////////////////////////////////////////////////////////////////////////////////	
	@Override
	public Client readByUserId(Long id) throws LogicException {
		try {
			Client client=clientDao.readByUserId(id);
	//		System.out.println("================2)"+client);
			Country country =countryDao.read(client.getCountry().getId());
			client.setCountry(country);
			User user = userDao.read(client.getUser().getId());
			client.setUser(user);
			List<Items> ilist=new ArrayList<>();
			client.getRecentitems().forEach((y)->{
				  try {
					ilist.add(itemsDao.read(y.getId()));
				  } catch (DaoException e) {					
					e.printStackTrace();}
			}); 
			client.setRecentitems(ilist);
						
			return client;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	@Override
	public Long readByUserId(Long id, boolean bJustId) throws LogicException {
		try {
			Client client=clientDao.readByUserId(id);							
			return client.getId();
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
}
