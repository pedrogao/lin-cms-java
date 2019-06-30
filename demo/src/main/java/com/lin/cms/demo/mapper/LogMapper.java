package com.lin.cms.demo.mapper;

import com.lin.cms.db.CrudMapper;
import com.lin.cms.demo.model.LogDO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface LogMapper extends CrudMapper<LogDO> {

    List<LogDO> findLogsByUsernameAndRange(@Param("name") String name, @Param("start") Date start, @Param("end") Date end);

    Integer countLogsByUsernameAndRange(@Param("name") String name, @Param("start") Date start, @Param("end") Date end);

    List<LogDO> searchLogsByUsernameAndKeywordAndRange(@Param("name") String name, @Param("keyword") String keyword, @Param("start") Date start, @Param("end") Date end);

    Integer countLogsByUsernameAndKeywordAndRange(@Param("name") String name, @Param("keyword") String keyword, @Param("start") Date start, @Param("end") Date end);

    List<String> getUserNames();
}