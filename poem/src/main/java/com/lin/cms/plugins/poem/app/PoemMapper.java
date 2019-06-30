package com.lin.cms.plugins.poem.app;

import com.lin.cms.db.CrudMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PoemMapper extends CrudMapper<PoemDO> {

    Integer getCount();

    List<PoemDO> findPoemsByAuthor(@Param("author") String author);
}