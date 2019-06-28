package com.lin.cms.demo.service;

import com.lin.cms.demo.model.BookPO;
import com.lin.cms.demo.validators.book.CreateOrUpdateBookValidator;
import com.lin.cms.demo.service.base.Service;

public interface BookService extends Service<BookPO> {
    void createBook(CreateOrUpdateBookValidator validator);

    BookPO getBookByKeyword(String q);

    void updateBook(BookPO book, CreateOrUpdateBookValidator validator);

    BookPO findOneByIdAndDeleteTime(Integer id);
}
