package com.lin.cms.plugins.poem.app;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PoemMapper extends BaseMapper<PoemDO> {

    Integer getCount();

    List<PoemDO> findPoemsByAuthor(@Param("author") String author);
}