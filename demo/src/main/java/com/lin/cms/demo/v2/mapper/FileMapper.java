package com.lin.cms.demo.v2.mapper;

import com.lin.cms.demo.v2.model.FileDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

/**
 * @author pedro
 * @since 2019-11-30
 */
@Service("fileMapper2")
public interface FileMapper extends BaseMapper<FileDO> {

    FileDO selectByMd5(@Param("md5") String md5);

    int selectCountByMd5(@Param("md5") String md5);
}
