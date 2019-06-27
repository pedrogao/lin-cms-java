package com.lin.cms.demo.service;

import com.lin.cms.demo.model.BookPO;
import com.lin.cms.demo.service.base.Service;
import com.lin.cms.demo.validators.CreateOrUpdateBookValidator;

public interface BookService extends Service<BookPO> {
    void createBook(CreateOrUpdateBookValidator validator);

    BookPO getBookByKeyword(String q);

    void updateBook(BookPO book, CreateOrUpdateBookValidator validator);

    BookPO findOneByIdAndDeleteTime(Integer id);
}
