package org.itstep.repository;

import org.itstep.entities.Color;
import org.springframework.data.repository.CrudRepository;
//import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Component;

@Component
public interface ColorRepository extends CrudRepository <Color, Long> {

}
