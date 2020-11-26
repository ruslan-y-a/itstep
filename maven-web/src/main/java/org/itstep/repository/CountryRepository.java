package org.itstep.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.itstep.entities.Country;

public interface CountryRepository  extends CrudRepository <Country, Long> {

}