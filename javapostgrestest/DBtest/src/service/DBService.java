package service;

import java.util.List;

import tabs.Entity;

public interface DBService {
	List<Entity> read(String dbName) throws LogicException;
	List<Entity> categoryListing(Long idCategotes[]) throws LogicException;
	List<Entity> tagList(Long idCategotes[]) throws LogicException;
	List<Entity> imgList(Long idItem) throws LogicException;
	Entity read(String dbName,Long id) throws LogicException;
	void save(Entity entity) throws LogicException;
	boolean delete(String dbName,Long id) throws LogicException;
	boolean hasDbDao();
	
}
