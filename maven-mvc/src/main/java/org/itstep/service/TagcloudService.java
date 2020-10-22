package org.itstep.service;

import java.util.List;

import org.itstep.entities.Tagcloud;
import org.itstep.repository.TagcloudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Component
@Service
@Transactional
public class TagcloudService {
/*	@Autowired 
	private TagcloudRepository repo;
	
	public TagcloudService(TagcloudRepository repo) {
		this.repo = repo;
	}
	public List<Tagcloud> findAll() {return (List<Tagcloud>) repo.findAll();}	 	   
    public void save(Tagcloud tagcloud) {repo.save(tagcloud);}        
    public Tagcloud findById(Long id) {return repo.findById(id).orElse(null);}            
    public void delete(Long id) {repo.deleteById(id);}
    public void delete(List<Long> ids) {for(Long id : ids) {repo.deleteById(id);}}
    public void delete(Long []ids) {for(Long id : ids) {repo.deleteById(id);}}   */
}
