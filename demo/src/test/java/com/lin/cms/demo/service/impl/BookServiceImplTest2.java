package com.lin.cms.demo.service.impl;

import com.lin.cms.demo.dto.book.CreateOrUpdateBookDTO;
import com.lin.cms.demo.entity.Book;
import com.lin.cms.demo.mapper.BookMapper;
import com.lin.cms.demo.model.BookDO;
import com.lin.cms.demo.repository.BookRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // 数据操作后回滚
@Rollback
public class BookServiceImplTest2 {

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private BookRepository bookRepository;

    private Integer id;
    private String title = "千里之外";
    private String author = "pedro";
    private String image = "千里之外.png";
    private String summary = "千里之外，是周杰伦和费玉清一起发售的歌曲";

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createBook() {
        CreateOrUpdateBookDTO validator = new CreateOrUpdateBookDTO();
        validator.setAuthor(author);
        validator.setImage(image);
        validator.setSummary(summary);
        validator.setTitle(title);
        bookService.createBook(validator);
        Optional<BookDO> opt = bookRepository.findBookByTitleLikeAndDeleteTimeIsNull(title);

        if (opt.isPresent()) {
            assertEquals(opt.get().getAuthor(), author);
        } else {
            assertNotNull(opt.get());
        }
    }

    @Test
    public void getBookByKeyword() {
        BookDO bookDO = new BookDO();
        bookDO.setTitle(title);
        bookDO.setAuthor(author);
        bookDO.setImage(image);
        bookDO.setSummary(summary);
        bookRepository.save(bookDO);
        this.id = bookDO.getId();

        BookDO book = bookService.getBookByKeyword("%千里%");
        assertEquals(book.getTitle(), bookDO.getTitle());
    }

    @Test
    public void updateBook() {
        BookDO bookDO = new BookDO();
        bookDO.setTitle(title);
        bookDO.setAuthor(author);
        bookDO.setImage(image);
        bookDO.setSummary(summary);
        bookRepository.save(bookDO);
        this.id = bookDO.getId();

        String newTitle = "tttttttt";

        CreateOrUpdateBookDTO validator = new CreateOrUpdateBookDTO();
        validator.setAuthor(author);
        validator.setImage(image);
        validator.setSummary(summary);
        validator.setTitle(newTitle);

        Optional<BookDO> opt = bookRepository.findBookByIdAndDeleteTimeIsNull(id);

        assertNotNull(opt.get());

        BookDO bookdoo = new BookDO();
        BeanUtils.copyProperties(opt.get(), bookdoo);

        bookService.updateBook(bookdoo, validator);

        Optional<BookDO> opt1 = bookRepository.findBookByTitleAndDeleteTimeIsNull(newTitle);
        assertNotNull(opt1.get());
    }

    @Test
    public void findOneByIdAndDeleteTime() {
        BookDO bookDO = new BookDO();
        bookDO.setTitle(title);
        bookDO.setAuthor(author);
        bookDO.setImage(image);
        bookDO.setSummary(summary);
        bookRepository.save(bookDO);
        this.id = bookDO.getId();

        BookDO one = bookService.findOneByIdAndDeleteTime(this.id);
        assertEquals(one.getTitle(), title);
    }

    @Test
    public void findAll() {
        BookDO bookDO = new BookDO();
        bookDO.setTitle(title);
        bookDO.setAuthor(author);
        bookDO.setImage(image);
        bookDO.setSummary(summary);
        bookRepository.save(bookDO);
        this.id = bookDO.getId();

        List<BookDO> books = bookService.findAll();
        assertTrue(books.size() > 0);
    }

    @Test
    public void deleteById() {
        BookDO bookDO = new BookDO();
        bookDO.setTitle(title);
        bookDO.setAuthor(author);
        bookDO.setImage(image);
        bookDO.setSummary(summary);
        bookRepository.save(bookDO);
        this.id = bookDO.getId();

        bookRepository.softDeleteById(id);

        BookDO one = bookService.findOneByIdAndDeleteTime(this.id);
        assertNull(one);
    }
}