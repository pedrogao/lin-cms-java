package com.lin.cms.demo.service;

import com.lin.cms.demo.model.BookDO;
import com.lin.cms.demo.dto.book.CreateOrUpdateBookDTO;
import com.lin.cms.demo.service.base.Service;

public interface BookService extends Service<BookDO> {
    void createBook(CreateOrUpdateBookDTO validator);

    BookDO getBookByKeyword(String q);

    void updateBook(BookDO book, CreateOrUpdateBookDTO validator);

    BookDO findOneByIdAndDeleteTime(Integer id);
}
