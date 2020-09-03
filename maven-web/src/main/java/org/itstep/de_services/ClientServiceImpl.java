package org.itstep.de_services;

import java.util.ArrayList;
import java.util.List;

import org.itstep.daos.ClientDao;
import org.itstep.daos.CountryDao;
import org.itstep.daos.ItemsDao;
import org.itstep.daos.UserDao;
import org.itstep.entities.Client;
import org.itstep.entities.Country;
import org.itstep.entities.Items;
import org.itstep.entities.User;
import org.itstep.postgres.DaoException;
import org.itstep.service.LogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ClientServiceImpl implements ClientService {
	@Autowired	
	private ClientDao clientDao;
	@Autowired		
	private CountryDao countryDao;
	@Autowired		
	private UserDao userDao;
	@Autowired		
	private ItemsDao itemsDao;
	
	public void setClientDao(ClientDao clientDao) {this.clientDao = clientDao;}
	public void setCountryDao(CountryDao countryDao) {this.countryDao= countryDao;}
	public void setUserDao(UserDao userDao) {this.userDao= userDao;}
	public void setItemsDao(ItemsDao itemsDao) {this.itemsDao= itemsDao;}

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
