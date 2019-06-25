package com.lin.cms.demo.mapper;

import com.lin.cms.db.CrudMapper;
import com.lin.cms.demo.model.LogPO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface LogMapper extends CrudMapper<LogPO> {

    List<LogPO> findLogsByUsernameAndRange(@Param("name") String name, @Param("start") Date start, @Param("end") Date end);

    Integer CountLogsByUsernameAndRange(@Param("name") String name, @Param("start") Date start, @Param("end") Date end);

    List<LogPO> searchLogsByUsernameAndKeywordAndRange(@Param("name") String name, @Param("keyword") String keyword, @Param("start") Date start, @Param("end") Date end);

    Integer CountLogsByUsernameAndKeywordAndRange(@Param("name") String name, @Param("keyword") String keyword, @Param("start") Date start, @Param("end") Date end);

    List<String> getUserNames();
}