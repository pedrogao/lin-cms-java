package com.lin.cms.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lin.cms.demo.configure.Page;
import com.lin.cms.demo.model.LogDO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface LogMapper extends BaseMapper<LogDO> {

    IPage<LogDO> findLogsByUsernameAndRange(Page page, @Param("name") String name, @Param("start") Date start, @Param("end") Date end);

    Integer countLogsByUsernameAndRange(@Param("name") String name, @Param("start") Date start, @Param("end") Date end);

    IPage<LogDO> searchLogsByUsernameAndKeywordAndRange(Page page, @Param("name") String name, @Param("keyword") String keyword, @Param("start") Date start, @Param("end") Date end);

    Integer countLogsByUsernameAndKeywordAndRange(@Param("name") String name, @Param("keyword") String keyword, @Param("start") Date start, @Param("end") Date end);

    IPage<String> getUserNames(Page page);
}