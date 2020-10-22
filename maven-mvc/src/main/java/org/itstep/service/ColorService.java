package org.itstep.service;

import java.util.List;

import org.itstep.entities.Color;
import org.itstep.repository.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
@Component
@org.springframework.stereotype.Service
@Transactional
public class ColorService {
	@Autowired private ColorRepository repo;
		
	public ColorService(ColorRepository repo) {
		this.repo = repo;
	}
	public List<Color> findAll() {return (List<Color>) repo.findAll();}	 	   
    public void save(Color country) {repo.save(country);}        
    public Color findById(Long id) {return repo.findById(id).orElse(null);}            
    public void delete(Long id) {repo.deleteById(id);}
    public void delete(List<Long> ids) {for(Long id : ids) {repo.deleteById(id);}}
    public void delete(Long []ids) {for(Long id : ids) {repo.deleteById(id);}}       
}
