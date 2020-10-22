package org.itstep.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.itstep.entities.Country;
import org.itstep.repository.CountryRepository;

@Component
@Service
@Transactional
public class CountryService {
	@Autowired private CountryRepository repo;
		
	public CountryService(CountryRepository repo) {
		this.repo = repo;
	}
	public List<Country> findAll() {return (List<Country>) repo.findAll();}	 	   
    public void save(Country country) {repo.save(country);}        
    public Country findById(Long id) {return repo.findById(id).orElse(null);}            
    public void delete(Long id) {repo.deleteById(id);}
    public void delete(List<Long> ids) {for(Long id : ids) {repo.deleteById(id);}}
    public void delete(Long []ids) {for(Long id : ids) {repo.deleteById(id);}}
    //public List<Country> search(String keyword) {return repo.search(keyword);}	
}
