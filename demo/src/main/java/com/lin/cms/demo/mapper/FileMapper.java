package com.lin.cms.demo.mapper;

import com.lin.cms.demo.db.CrudMapper;
import com.lin.cms.demo.model.FilePO;
import org.apache.ibatis.annotations.Param;

public interface FileMapper extends CrudMapper<FilePO> {

    FilePO findOneByMd5(@Param("md5") String md5);
}