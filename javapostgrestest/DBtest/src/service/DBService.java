package service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tabs.Classification;
import tabs.Entity;

public interface DBService {
	void createCsvLoad(String dbName) throws LogicException;
	void create(String dbName, Map<String,String> mass) throws LogicException;
	void create(String dbName, ArrayList<String> fileds,ArrayList<String> values) throws LogicException;
/////////////////////////////////////////////////////////////////////	
	List<Entity> read(String dbName) throws LogicException;
	List<Entity> read(String dbName, String field, Object fValue) throws LogicException;
	List<Entity> read(String dbName, String field, String expr) throws LogicException;
	List<Entity> categoryListing(Long idCategotes[]) throws LogicException;
	List<Entity> tagList(Long idCategotes[]) throws LogicException;
	List<Entity> imgList(Long idItem) throws LogicException;
	Map<Long,Object> mapIdField(String name, String tarfield, String field, Object fValue) throws LogicException;
	Map<Long,Object> mapIdField(String name, String tarfield, String field, String expr) throws LogicException;
	Map<Long,Entity> mapEntities(String name) throws LogicException;
////////////////////////////////////////////////////////////////////////	
	List<Classification> itemCategories(Long id) throws LogicException;
	Long[] readID(String name, String field, Object fValue) throws LogicException;
	Entity read(String dbName,Long id) throws LogicException;
/////////////////////////////////////////////////////////////////	
	Long save(Entity entity) throws LogicException;
	void update(Entity entity) throws LogicException;
	Long save(String name, Long id, ArrayList<String> fields, ArrayList<String> values) throws LogicException;
////////////////////////////////////////////////////////////////	
	boolean delete(Entity entity) throws LogicException;
	boolean delete(String dbName,Long id) throws LogicException;
	boolean delete(String name,  String field, Object fValue) throws LogicException;
	void delete(String dbName,List<Long> ids) throws LogicException;
	boolean hasDbDao();
	////////////////////////////////////////////////
	void updateClassification(Long idItem, Long idCategory) throws LogicException;
	boolean updateClassificationFromCategory() throws LogicException; 
}
