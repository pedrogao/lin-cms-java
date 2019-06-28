package com.lin.cms.demo.service.impl;

import com.lin.cms.demo.mapper.BookMapper;
import com.lin.cms.demo.model.BookPO;
import com.lin.cms.demo.validators.book.CreateOrUpdateBookValidator;
import com.lin.cms.demo.service.BookService;
import com.lin.cms.demo.service.base.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl extends AbstractService<BookPO> implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Override
    public void createBook(CreateOrUpdateBookValidator validator) {
        BookPO book = new BookPO();
        book.setAuthor(validator.getAuthor());
        book.setTitle(validator.getTitle());
        book.setImage(validator.getImage());
        book.setSummary(validator.getSummary());
        this.save(book);
    }

    @Override
    public BookPO getBookByKeyword(String q) {
        BookPO book = bookMapper.getBookByKeyword(q);
        return book;
    }

    @Override
    public void updateBook(BookPO book, CreateOrUpdateBookValidator validator) {
        book.setAuthor(validator.getAuthor());
        book.setTitle(validator.getTitle());
        book.setImage(validator.getImage());
        book.setSummary(validator.getSummary());
        this.update(book);
    }

    @Override
    public BookPO findOneByIdAndDeleteTime(Integer id) {
        return bookMapper.findOneByIdAndDeleteTime(id);
    }
}
