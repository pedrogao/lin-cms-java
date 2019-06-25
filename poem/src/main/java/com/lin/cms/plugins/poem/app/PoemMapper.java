package com.lin.cms.plugins.poem.app;

import com.lin.cms.db.CrudMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PoemMapper extends CrudMapper<PoemPO> {

    Integer getCount();

    List<PoemPO> findPoemsByAuthor(@Param("author") String author);
}