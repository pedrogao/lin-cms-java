package com.lin.cms.demo.repository;

import com.lin.cms.demo.entity.Book;

import java.util.Optional;


public interface BookRepository extends SoftCrudRepository<Book, Integer> {

    Optional<Book> findBookByIdAndDeleteTimeIsNull(Integer id);

    Optional<Book> findBookByTitleLikeAndDeleteTimeIsNull(String q);

    Optional<Book> findBookByTitleAndDeleteTimeIsNull(String title);
}
