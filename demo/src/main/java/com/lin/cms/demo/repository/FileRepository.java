package com.lin.cms.demo.repository;

import com.lin.cms.demo.model.FileDO;
import org.springframework.data.repository.CrudRepository;

public interface FileRepository extends CrudRepository<FileDO, Integer> {
    FileDO findTopByMd5(String md5);
}
