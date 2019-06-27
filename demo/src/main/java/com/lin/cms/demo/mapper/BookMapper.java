package com.lin.cms.demo.mapper;

import com.lin.cms.db.CrudMapper;
import com.lin.cms.demo.model.BookPO;
import org.apache.ibatis.annotations.Param;

public interface BookMapper extends CrudMapper<BookPO> {

    BookPO getBookByKeyword(@Param("q") String q);

    BookPO findOneByIdAndDeleteTime(@Param("id") Integer id);
}