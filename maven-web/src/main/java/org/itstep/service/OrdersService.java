package org.itstep.service;

import java.util.List;

import org.itstep.entities.Orders;
import org.itstep.entities.Orderstatus;
import org.itstep.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class OrdersService {
/*	@Autowired 	private OrdersRepository repo;
	
	public OrdersService(OrdersRepository repo) {
		this.repo = repo;
	}
	public List<Orders> findAll() {return (List<Orders>) repo.findAll();}	 	   
    public void save(Orders orders) {
    	if (orders.getNumber()==null || orders.getNumber()==0) {orders.setNumber(this.getMaxOrder());}
    	synchronized(repo) {
    		repo.save(orders); 
    		}
    }        
    public Orders findById(Long id) {return repo.findById(id).orElse(null);}            
    public void delete(Long id) {repo.deleteById(id);}
    public void delete(List<Long> ids) {for(Long id : ids) {repo.deleteById(id);}}
    public void delete(Long []ids) {for(Long id : ids) {repo.deleteById(id);}}
   
    public List<Orders> orderdItems(Long client) {return repo.orderdItems(client);}
    public List<Orders> search(Orderstatus status, Long client) {return repo.search(status.ordinal(), client);}
    public List<Orders> clientOrderedItems(Long client) {return repo.clientOrderedItems(client);}
    public synchronized Integer getMaxOrder() {
    	synchronized(repo) {
    	     return repo.getMaxOrder()+1;
    	     } 
    	 }
/*    
	 	 
	 List<Orders> search(@Param("status") Orderstatus status, @Param("client") Long client);
	 List<Orders> clientOrderedItems(@Param("status") Orderstatus status, @Param("client") Long client);
	List<Orders> getMaxOrder();
 */   
    
}
