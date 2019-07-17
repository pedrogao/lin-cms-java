package com.lin.cms.demo.repository;

import com.lin.cms.demo.model.BookDO;

import java.util.Optional;


public interface BookRepository extends SoftCrudRepository<BookDO, Integer> {

    Optional<BookDO> findBookByIdAndDeleteTimeIsNull(Integer id);

    Optional<BookDO> findBookByTitleLikeAndDeleteTimeIsNull(String q);

    Optional<BookDO> findBookByTitleAndDeleteTimeIsNull(String title);
}
