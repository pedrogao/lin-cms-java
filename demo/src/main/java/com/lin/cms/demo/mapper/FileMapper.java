package com.lin.cms.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.cms.demo.model.FileDO;
import org.apache.ibatis.annotations.Param;

public interface FileMapper extends BaseMapper<FileDO> {

    FileDO findOneByMd5(@Param("md5") String md5);
}