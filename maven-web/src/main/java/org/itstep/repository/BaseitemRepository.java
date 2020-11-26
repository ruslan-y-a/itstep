package org.itstep.repository;


import java.util.List;

import org.itstep.daos.DaoException;
import org.itstep.entities.Baseitem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;


public interface BaseitemRepository { //extends CrudRepository <Baseitem, Long> {  //Jpa
	 //@Query(value = "SELECT u FROM Baseitem u WHERE u.itemid = :itemid")	   
	 //public List<Baseitem> readItemRow(@Param("itemid") Long itemid) throws DaoException;
}
