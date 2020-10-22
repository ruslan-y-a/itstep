package org.itstep.repository;

import org.itstep.entities.Size;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
public interface SizeRepository extends CrudRepository <Size, Long> {

}
