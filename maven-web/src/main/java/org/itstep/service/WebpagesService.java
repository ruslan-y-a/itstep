package org.itstep.service;

import java.util.List;

import org.itstep.entities.Webpages;
import org.itstep.repository.WebpagesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Component
@Service
@Transactional
public class WebpagesService {
	@Autowired 
	private WebpagesRepository repo;
	
	public WebpagesService(WebpagesRepository repo) {
		this.repo = repo;
	}
	public List<Webpages> findAll() {return (List<Webpages>) repo.findAll();}	 	   
    public void save(Webpages webpages) {repo.save(webpages);}        
    public Webpages findById(Long id) {return repo.findById(id).orElse(null);}            
    public void delete(Long id) {repo.deleteById(id);}
    public void delete(List<Long> ids) {for(Long id : ids) {repo.deleteById(id);}}
    public void delete(Long []ids) {for(Long id : ids) {repo.deleteById(id);}}   
    
    public Webpages fingByEntityId(Long entityid, String entity) {return repo.fingByEntityId(entityid,entity);} 
    public Webpages readEntityId(String url) {return repo.readEntityId(url);} 
}
