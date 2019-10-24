package com.lin.cms.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.cms.demo.model.BookDO;
import org.apache.ibatis.annotations.Param;

public interface BookMapper extends BaseMapper<BookDO> {

    BookDO getBookByKeyword(@Param("q") String q);

    BookDO findOneByIdAndDeleteTime(@Param("id") Long id);

    BookDO findOneByTitle(@Param("title") String title);
}