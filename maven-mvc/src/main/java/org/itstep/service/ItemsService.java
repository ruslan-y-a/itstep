package org.itstep.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.itstep.entities.Items;
import org.itstep.repository.ItemsRepository;

@Component
@Service
@Transactional
public class ItemsService {
	/*@Autowired 
	private ItemsRepository repo;
		
	public ItemsService(ItemsRepository repo) {
		this.repo = repo;
	}
	public List<Items> findAll() {return (List<Items>) repo.findAll();}	 	   
    public void save(Items items) {repo.save(items);}        
    public Items findById(Long id) {return repo.findById(id).orElse(null);}            
    public void delete(Long id) {repo.deleteById(id);}
    public void delete(List<Long> ids) {for(Long id : ids) {repo.deleteById(id);}}
    public void delete(Long []ids) {for(Long id : ids) {repo.deleteById(id);}}
    //public List<Items> search(String keyword) {return repo.search(keyword);}*/
}
