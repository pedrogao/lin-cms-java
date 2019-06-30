package com.lin.cms.demo.mapper;

import com.lin.cms.db.CrudMapper;
import com.lin.cms.demo.model.FileDO;
import org.apache.ibatis.annotations.Param;

public interface FileMapper extends CrudMapper<FileDO> {

    FileDO findOneByMd5(@Param("md5") String md5);
}