package com.lin.cms.demo.service.impl;

import com.lin.cms.demo.mapper.BookMapper;
import com.lin.cms.demo.model.BookDO;
import com.lin.cms.demo.dto.book.CreateOrUpdateBookDTO;
import com.lin.cms.demo.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Override
    public void createBook(CreateOrUpdateBookDTO validator) {
        BookDO book = new BookDO();
        book.setAuthor(validator.getAuthor());
        book.setTitle(validator.getTitle());
        book.setImage(validator.getImage());
        book.setSummary(validator.getSummary());
        bookMapper.insert(book);
    }

    @Override
    public BookDO getBookByKeyword(String q) {
        BookDO book = bookMapper.getBookByKeyword(q);
        return book;
    }

    @Override
    public void updateBook(BookDO book, CreateOrUpdateBookDTO validator) {
        book.setAuthor(validator.getAuthor());
        book.setTitle(validator.getTitle());
        book.setImage(validator.getImage());
        book.setSummary(validator.getSummary());
        bookMapper.updateById(book);
    }

    @Override
    public BookDO findOneByIdAndDeleteTime(Integer id) {
        BookDO book = bookMapper.findOneByIdAndDeleteTime(id);
        return book;
    }

    @Override
    public List<BookDO> findAll() {
        List<BookDO> books = bookMapper.selectList(null);
        return books;
    }

    @Override
    public void deleteById(Integer id) {
        bookMapper.deleteById(id);
    }
}
