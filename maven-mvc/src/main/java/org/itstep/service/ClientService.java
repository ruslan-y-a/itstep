package org.itstep.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.itstep.entities.Client;
import org.itstep.repository.ClientRepository;

@Component
@Service
@Transactional
public class ClientService {
	/*@Autowired 
	private ClientRepository repo;
		
	public ClientService(ClientRepository repo) {
		this.repo = repo;
	}
	public List<Client> findAll() {return (List<Client>) repo.findAll();}	 	   
    public void save(Client country) {repo.save(country);}        
    public Client findById(Long id) {return repo.findById(id).orElse(null);}            
    public void delete(Long id) {repo.deleteById(id);}
    public void delete(List<Long> ids) {for(Long id : ids) {repo.deleteById(id);}}
    public void delete(Long []ids) {for(Long id : ids) {repo.deleteById(id);}}
  //  public List<Client> search(String keyword) {return repo.search(keyword);}    
    public Long findByUserId(Long id) {return repo.findByUserId(id);}
    public Client readByUserId(Long id) {return repo.readByUserId(id);}*/
}
