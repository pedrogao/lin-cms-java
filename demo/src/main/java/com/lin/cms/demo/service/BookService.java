package com.lin.cms.demo.service;

import com.lin.cms.demo.model.BookDO;
import com.lin.cms.demo.dto.book.CreateOrUpdateBookDTO;

import java.util.List;

public interface BookService {
    void createBook(CreateOrUpdateBookDTO validator);

    BookDO getBookByKeyword(String q);

    void updateBook(BookDO book, CreateOrUpdateBookDTO validator);

    BookDO findOneByIdAndDeleteTime(Long id);

    List<BookDO> findAll();

    void deleteById(Long id);
}
