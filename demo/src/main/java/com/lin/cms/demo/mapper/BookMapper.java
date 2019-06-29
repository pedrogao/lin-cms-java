package com.lin.cms.demo.mapper;

import com.lin.cms.db.CrudMapper;
import com.lin.cms.demo.model.BookDO;
import org.apache.ibatis.annotations.Param;

public interface BookMapper extends CrudMapper<BookDO> {

    BookDO getBookByKeyword(@Param("q") String q);

    BookDO findOneByIdAndDeleteTime(@Param("id") Integer id);
}