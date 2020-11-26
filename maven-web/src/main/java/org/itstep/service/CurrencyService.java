package org.itstep.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.itstep.entities.Currency;
import org.itstep.repository.CurrencyRepository;

@Component
@Service
@Transactional
public class CurrencyService {
	@Autowired 
	private CurrencyRepository repo;

	public CurrencyService(CurrencyRepository repo) {
		this.repo = repo;
	}
	public List<Currency> findAll() {return (List<Currency>) repo.findAll();}	 	   
    public void save(Currency currency) {repo.save(currency);}        
    public Currency findById(Long id) {return repo.findById(id).orElse(null);}            
    public void delete(Long id) {repo.deleteById(id);}
    public void delete(List<Long> ids) {for(Long id : ids) {repo.deleteById(id);}}
    public void delete(Long []ids) {for(Long id : ids) {repo.deleteById(id);}}
    //public List<Currency> search(String keyword) {return repo.search(keyword);}
}
