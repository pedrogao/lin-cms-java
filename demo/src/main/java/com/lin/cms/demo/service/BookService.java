package com.lin.cms.demo.service;

import com.lin.cms.demo.model.BookDO;
import com.lin.cms.demo.dto.book.CreateOrUpdateBookDTO;

import java.util.List;

public interface BookService {

    boolean createBook(CreateOrUpdateBookDTO validator);

    List<BookDO> getBookByKeyword(String q);

    boolean updateBook(BookDO book, CreateOrUpdateBookDTO validator);

    BookDO getById(Long id);

    List<BookDO> findAll();

    boolean deleteById(Long id);
}
