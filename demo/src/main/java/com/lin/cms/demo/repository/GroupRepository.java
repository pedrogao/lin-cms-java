package com.lin.cms.demo.repository;

import com.lin.cms.demo.model.GroupDO;
import org.springframework.data.repository.CrudRepository;


public interface GroupRepository extends CrudRepository<GroupDO, Integer> {

    GroupDO findTopByName(String name);
}
