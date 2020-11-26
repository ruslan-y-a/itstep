package org.itstep.service;

import java.util.List;

import org.itstep.daos.DaoException;
import org.itstep.entities.Baseitem;
import org.itstep.repository.BaseitemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Component
@Service
@Transactional
public class BaseitemService {
	/*
	@Autowired private BaseitemRepository repo;	

	public List<Baseitem> findAll() {return (List<Baseitem>) repo.findAll();}	 	   
    public void save(Baseitem country) {repo.save(country);}        
    public Baseitem findById(Long id) {return repo.findById(id).orElse(null);}            
    public void delete(Long id) {repo.deleteById(id);}
    public void delete(List<Long> ids) {for(Long id : ids) {repo.deleteById(id);}}
    public void delete(Long []ids) {for(Long id : ids) {repo.deleteById(id);}}
    public List<Baseitem> readItemRow(Long itemid) throws LogicException {
    	try {return repo.readItemRow(itemid);
		} catch (DaoException e) {
			e.printStackTrace(); throw new LogicException(e); 
		}
    }
    */
}
