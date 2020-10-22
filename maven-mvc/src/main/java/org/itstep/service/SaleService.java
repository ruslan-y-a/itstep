package org.itstep.service;

import java.util.List;

import org.itstep.entities.Sale;
import org.itstep.repository.SaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//@Component
//@Service
//@Transactional
public class SaleService {
/*	@Autowired private SaleRepository repo;
		
	public SaleService(SaleRepository repo) {
		this.repo = repo;
	}
	public List<Sale> findAll() {return (List<Sale>) repo.findAll();}	 	   
    public void save(Sale country) {repo.save(country);}        
    public Sale findById(Long id) {return repo.findById(id).orElse(null);}            
    public void delete(Long id) {repo.deleteById(id);}
    public void delete(List<Long> ids) {for(Long id : ids) {repo.deleteById(id);}}
    public void delete(Long []ids) {for(Long id : ids) {repo.deleteById(id);}}  */
}
