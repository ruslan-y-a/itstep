package de_services;

import java.util.List;

import entities.Items;
import entities.ItemsSort;
import service.LogicException;

public interface ItemsService extends BaseService<Items> {
	List<Items> findAll() throws LogicException;
	Long save(Items items) throws LogicException;
	void delete(Long id) throws LogicException;
	Items read(Long id) throws LogicException;
	List<Items> search(String search) throws LogicException;
	List<Items> search(String search, ItemsSort itemsSort, Integer limit, Integer page) throws LogicException;
	List<Items> search(Integer scategory, ItemsSort itemsSort, Integer limit, Integer page) throws LogicException;
	List<Items> readPage(ItemsSort itemsSort, Integer limit, Integer page) throws LogicException;
	List<Items> search(List<Integer> sclassification, ItemsSort itemsSort, Integer limit, Integer page) throws LogicException;
	List<Items> searchCategories(List<Long> sclassification, ItemsSort itemsSort, Integer limit, Integer page) throws LogicException;
	List<Items> searchListsCategories(List<List<Integer>> sclassification, ItemsSort itemsSort, Integer limit, Integer page) throws LogicException;
	
	///////////////////////////////////////////////////////////////
	List<Items> search(List<Integer> sclassification, ItemsSort itemsSort, Integer limit, Integer page, Boolean instock) throws LogicException;
	List<Items> searchListsCategories(List<List<Integer>> sclassification, ItemsSort itemsSort, Integer limit, Integer page, boolean stock) throws LogicException;
}
