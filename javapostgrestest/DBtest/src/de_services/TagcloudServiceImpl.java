package de_services;

import java.util.ArrayList;
import java.util.List;

import daos.ClassificationDao;
import daos.WebpagesDao;
import daos.TagcloudDao;
import entities.Tagcloud;
import entities.Webpages;
import entities.Classification;
import postgres.DaoException;
import service.LogicException;

public class TagcloudServiceImpl implements TagcloudService {

	private ClassificationDao classificationDao;
	private WebpagesDao webpagesDao;
	private TagcloudDao tagcloudDao;	
	
	public void setClassificationDao(ClassificationDao classificationDao) {
		this.classificationDao = classificationDao;}
	public void setWebpagesDao(WebpagesDao webpagesDao) {
		this.webpagesDao= webpagesDao;}
	public void setTagcloudDao(TagcloudDao tagcloudDao) {
		this.tagcloudDao= tagcloudDao;	}

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
	public Tagcloud read(Long id) throws LogicException {
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
	
}
