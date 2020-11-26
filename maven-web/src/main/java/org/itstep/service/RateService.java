package org.itstep.service;

import java.util.List;

import org.itstep.entities.Rate;
import org.itstep.repository.RateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Component
@Service
@Transactional
public class RateService {
	@Autowired private RateRepository repo;

	public RateService(RateRepository repo) {this.repo = repo;}
	
	public List<Rate> findAll() {return (List<Rate>) repo.findAll();}	 	   
    public void save(Rate rate) {repo.save(rate);}        
    public Rate findById(Long id) {return repo.findById(id).orElse(null);}            
    public void delete(Long id) {repo.deleteById(id);}
    public void delete(List<Long> ids) {for(Long id : ids) {repo.deleteById(id);}}
    public void delete(Long []ids) {for(Long id : ids) {repo.deleteById(id);}}
}
