package com.lin.cms.demo.mapper;

import com.lin.cms.demo.db.Mapper;
import com.lin.cms.demo.model.Log;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface LogMapper extends Mapper<Log> {

    List<Log> findLogsByUsernameAndRange(@Param("name") String name, @Param("start") Date start, @Param("end") Date end);

    Integer CountLogsByUsernameAndRange(@Param("name") String name, @Param("start") Date start, @Param("end") Date end);

    List<Log> searchLogsByUsernameAndKeywordAndRange(@Param("name") String name, @Param("keyword") String keyword, @Param("start") Date start, @Param("end") Date end);

    Integer CountLogsByUsernameAndKeywordAndRange(@Param("name") String name, @Param("keyword") String keyword, @Param("start") Date start, @Param("end") Date end);

    List<String> getUserNames();
}