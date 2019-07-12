package com.lin.cms.demo.repository;

import com.lin.cms.demo.entity.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;


public interface BookRepository extends CrudRepository<Book, Integer> {

    @Query(value = "SELECT b.* FROM book b WHERE b.id = :id AND b.delete_time IS NULL", nativeQuery = true)
    Book findBookByIdAndDeleteTime(@Param("id") Integer id);

    Book findBookByTitleLike(String q);
}
