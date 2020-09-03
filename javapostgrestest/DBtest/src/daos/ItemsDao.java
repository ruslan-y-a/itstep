package daos;

import java.util.List;
import entities.Items;
import entities.ItemsSort;
import postgres.DaoException;

public interface ItemsDao extends Dao<Items> {
	Long create(Items items) throws DaoException;
	Items read(Long id) throws DaoException;
	void update(Items items) throws DaoException;
	void delete(Long id) throws DaoException;
	List<Items> read() throws DaoException;
	List<Items> search(String search) throws DaoException;
	List<Items> search(String search, ItemsSort itemsSort, Integer limit, Integer page) throws DaoException;
	List<Items> search(Integer category, ItemsSort itemsSort, Integer limit, Integer page) throws DaoException;
	List<Items> search(List<Integer> classification, ItemsSort itemsSort, Integer limit, Integer page) throws DaoException;
	List<Items> readPage(ItemsSort itemsSort, Integer limit, Integer page) throws DaoException;
	List<Items> searchCategories(List<Long> sclassification, ItemsSort itemsSort, Integer limit, Integer page) throws DaoException;	
	List<Items> searchListsCategories(List<List<Integer>> sclassification, ItemsSort itemsSort, Integer limit, Integer page) throws DaoException;
}
