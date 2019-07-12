package com.lin.cms.demo.service.impl;

import com.lin.cms.demo.entity.Book;
import com.lin.cms.demo.mapper.BookMapper;
import com.lin.cms.demo.model.BookDO;
import com.lin.cms.demo.dto.book.CreateOrUpdateBookDTO;
import com.lin.cms.demo.repository.BookRepository;
import com.lin.cms.demo.service.BookService;
import com.lin.cms.demo.service.base.AbstractService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl extends AbstractService<BookDO> implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void createBook(CreateOrUpdateBookDTO validator) {
        BookDO book = new BookDO();
        book.setAuthor(validator.getAuthor());
        book.setTitle(validator.getTitle());
        book.setImage(validator.getImage());
        book.setSummary(validator.getSummary());
        this.save(book);
    }

    @Override
    public BookDO getBookByKeyword(String q) {
        Book book = bookRepository.findBookByTitleLike(q);
        BookDO bookDO = new BookDO();
        BeanUtils.copyProperties(book, bookDO);
        return bookDO;
        // BookDO book = bookMapper.getBookByKeyword(q);
        // return book;
    }

    @Override
    public void updateBook(BookDO book, CreateOrUpdateBookDTO validator) {
        book.setAuthor(validator.getAuthor());
        book.setTitle(validator.getTitle());
        book.setImage(validator.getImage());
        book.setSummary(validator.getSummary());
        this.update(book);
    }

    @Override
    public BookDO findOneByIdAndDeleteTime(Integer id) {
        Book book = bookRepository.findBookByIdAndDeleteTime(id);
        BookDO bookDO = new BookDO();
        BeanUtils.copyProperties(book, bookDO);
        return bookDO;
        // return bookMapper.findOneByIdAndDeleteTime(id);
    }
}
