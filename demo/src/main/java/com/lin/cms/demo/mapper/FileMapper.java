package com.lin.cms.demo.mapper;

import com.lin.cms.demo.db.Mapper;
import com.lin.cms.demo.model.File;
import org.apache.ibatis.annotations.Param;

public interface FileMapper extends Mapper<File> {

    File findOneByMd5(@Param("md5") String md5);
}