package com.lin.cms.demo.service.impl;

import com.lin.cms.demo.entity.Book;
import com.lin.cms.demo.model.BookDO;
import com.lin.cms.demo.dto.book.CreateOrUpdateBookDTO;
import com.lin.cms.demo.repository.BookRepository;
import com.lin.cms.demo.service.BookService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void createBook(CreateOrUpdateBookDTO validator) {
        BookDO book = new BookDO();
        setBookData(book, validator);
    }

    @Override
    public BookDO getBookByKeyword(String q) {
        Optional<BookDO> opt = bookRepository.findBookByTitleLikeAndDeleteTimeIsNull(q);
        return getBookDO(opt);
    }

    @Override
    public void updateBook(BookDO book, CreateOrUpdateBookDTO validator) {
        setBookData(book, validator);
    }

    private void setBookData(BookDO book, CreateOrUpdateBookDTO validator) {
        book.setAuthor(validator.getAuthor());
        book.setTitle(validator.getTitle());
        book.setImage(validator.getImage());
        book.setSummary(validator.getSummary());
        bookRepository.save(book);
    }

    @Override
    public BookDO findOneByIdAndDeleteTime(Integer id) {
        Optional<BookDO> opt = bookRepository.findBookByIdAndDeleteTimeIsNull(id);
        return getBookDO(opt);
        // return bookMapper.findOneByIdAndDeleteTime(id);
    }

    @Override
    public List<BookDO> findAll() {
        Iterable<BookDO> books = bookRepository.findAll();
        List<BookDO> res = new ArrayList<>();
        books.forEach(book -> {
            BookDO bookDO = new BookDO();
            BeanUtils.copyProperties(book, bookDO);
            res.add(bookDO);
        });
        return res;
    }

    @Override
    public void deleteById(Integer id) {
        bookRepository.deleteById(id);
    }

    private BookDO getBookDO1(Optional<Book> opt) {
        if (!opt.isPresent()) {
            return null;
        }
        BookDO bookDO = new BookDO();
        BeanUtils.copyProperties(opt.get(), bookDO);
        return bookDO;
    }

    private BookDO getBookDO(Optional<BookDO> opt) {
        if (!opt.isPresent()) {
            return null;
        }
        return opt.get();
    }
}
