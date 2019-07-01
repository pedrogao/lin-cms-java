package com.lin.cms.demo.service.impl;

import com.lin.cms.demo.mapper.BookMapper;
import com.lin.cms.demo.model.BookDO;
import com.lin.cms.demo.validators.book.CreateOrUpdateBookValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // 数据操作后回滚
@Rollback
public class BookServiceImplTest {

    @Autowired
    private BookServiceImpl bookService;

    @Autowired
    private BookMapper bookMapper;

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
        CreateOrUpdateBookValidator validator = new CreateOrUpdateBookValidator();
        validator.setAuthor(author);
        validator.setImage(image);
        validator.setSummary(summary);
        validator.setTitle(title);
        bookService.createBook(validator);

        BookDO bookDO = bookService.findBy("title", title);
        assertEquals(bookDO.getAuthor(), author);
    }

    @Test
    public void getBookByKeyword() {
        BookDO bookDO = new BookDO();
        bookDO.setTitle(title);
        bookDO.setAuthor(author);
        bookDO.setImage(image);
        bookDO.setSummary(summary);
        bookMapper.insertSelective(bookDO);
        this.id = bookDO.getId();

        BookDO book = bookService.getBookByKeyword("千里");
        assertEquals(book.getTitle(), bookDO.getTitle());
    }

    @Test
    public void updateBook() {
        BookDO bookDO = new BookDO();
        bookDO.setTitle(title);
        bookDO.setAuthor(author);
        bookDO.setImage(image);
        bookDO.setSummary(summary);
        bookMapper.insertSelective(bookDO);
        this.id = bookDO.getId();

        String newTitle = "tttttttt";

        CreateOrUpdateBookValidator validator = new CreateOrUpdateBookValidator();
        validator.setAuthor(author);
        validator.setImage(image);
        validator.setSummary(summary);
        validator.setTitle(newTitle);

        BookDO found = bookService.findById(id);

        bookService.updateBook(found, validator);

        BookDO found1 = bookService.findBy("title", newTitle);

        assertNotNull(found1);
    }

    @Test
    public void findOneByIdAndDeleteTime() {
        BookDO bookDO = new BookDO();
        bookDO.setTitle(title);
        bookDO.setAuthor(author);
        bookDO.setImage(image);
        bookDO.setSummary(summary);
        bookMapper.insertSelective(bookDO);
        this.id = bookDO.getId();

        bookDO.setDeleteTime(new Date());
        bookMapper.updateByPrimaryKeySelective(bookDO);

        BookDO one = bookService.findOneByIdAndDeleteTime(id);
        assertNull(one);
    }
}