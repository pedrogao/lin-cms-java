package com.lin.cms.demo.service;

import com.lin.cms.demo.model.BookDO;
import com.lin.cms.demo.validators.book.CreateOrUpdateBookValidator;
import com.lin.cms.struct.service.base.Service;

public interface BookService extends Service<BookDO> {
    void createBook(CreateOrUpdateBookValidator validator);

    BookDO getBookByKeyword(String q);

    void updateBook(BookDO book, CreateOrUpdateBookValidator validator);

    BookDO findOneByIdAndDeleteTime(Integer id);
}
