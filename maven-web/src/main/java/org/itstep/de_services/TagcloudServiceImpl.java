package org.itstep.de_services;

import java.util.ArrayList;
import java.util.List;

import org.itstep.daos.ClassificationDao;
import org.itstep.daos.WebpagesDao;
import org.itstep.daos.TagcloudDao;
import org.itstep.entities.Tagcloud;
import org.itstep.entities.Webpages;
import org.itstep.repository.WebpagesRepository;
import org.itstep.entities.Classification;
import org.itstep.daos.DaoException;
import org.itstep.service.LogicException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
@Component
@Scope("prototype")
public class TagcloudServiceImpl implements TagcloudService {
	@Autowired	
	private ClassificationDao classificationDao;
	@Autowired		
	private WebpagesDao webpagesDao;
	@Autowired private WebpagesRepository wrepo;
	@Autowired		
	private TagcloudDao tagcloudDao;	
	
	public void setClassificationDao(ClassificationDao classificationDao) {this.classificationDao = classificationDao;}
	public void setWebpagesDao(WebpagesDao webpagesDao) {this.webpagesDao= webpagesDao;}
	public void setTagcloudDao(TagcloudDao tagcloudDao) {this.tagcloudDao= tagcloudDao;	}

	@Override
	public List<Tagcloud> findAll() throws LogicException {
		try {
			List<Tagcloud> list = tagcloudDao.read();
			list.forEach((x) -> {				
				try {
					Webpages webpages =webpagesDao.read(x.getWebpages().getId());
					x.setWebpages(webpages); 	
					List<Classification> ilist=new ArrayList<>();
					x.getClassification().forEach((y)->{
						  try {
							ilist.add(classificationDao.read(y.getId()));
						  } catch (DaoException e) {					
							e.printStackTrace();}
					}); 
					x.setClassification(ilist);
					
				} catch (DaoException e) {
					e.printStackTrace();}
				
						
			});
			return list;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public Long save(Tagcloud tagcloud) throws LogicException {
		try {
			Long id = tagcloud.getId();
			if(id == null) {
				id = tagcloudDao.create(tagcloud);
				tagcloud.setId(id);
			} else {
				tagcloudDao.update(tagcloud);
			}
			
			if (tagcloud.getWebpages()!=null && tagcloud.getWebpages().getId()!=null) {
				Webpages webpages =wrepo.findById(tagcloud.getWebpages().getId()).orElse(null);	
				if (tagcloud.getWebpages().getId() !=null) {
					webpages.setEntityid(tagcloud.getWebpages().getId());}	
				wrepo.save(webpages);
			}
			
			return id;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}

	@Override
	public void delete(Long id) throws LogicException {
		try {
			tagcloudDao.delete(id);
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	@Override
	public Tagcloud findById(Long id) throws LogicException {
		try {
			Tagcloud tagcloud=tagcloudDao.read(id);
			
			Webpages webpages =webpagesDao.read(tagcloud.getWebpages().getId());
			tagcloud.setWebpages(webpages); 	
			List<Classification> ilist=new ArrayList<>();
			tagcloud.getClassification().forEach((y)->{
				  try {
					ilist.add(classificationDao.read(y.getId()));
				  } catch (DaoException e) {					
					e.printStackTrace();}
			}); 
			tagcloud.setClassification(ilist);
						
			return tagcloud;
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	
	@Override
	public void delete(List<Long> ids) throws LogicException {
		try {
			for(Long id : ids) {
				tagcloudDao.delete(id);
			}
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
	//////////////////////////////////////////////////////////////////
	@Override
	public List<Long> readByWP(Long id) throws LogicException {
		try {			
				return tagcloudDao.readByWP(id);			
		} catch(DaoException e) {
			throw new LogicException(e);
		}
	}
//////////////////////////////////////////////////////////////////////	
}
