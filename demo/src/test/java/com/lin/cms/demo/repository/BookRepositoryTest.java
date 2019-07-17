package com.lin.cms.demo.repository;

import com.lin.cms.demo.entity.Book;
import com.lin.cms.demo.model.BookDO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // 数据操作后回滚
@Rollback
public class BookRepositoryTest {

    @Autowired
    private BookRepository bookRepository;


    private Integer id;
    private String title = "千里之外";
    private String author = "pedro";
    private String image = "千里之外.png";
    private String summary = "千里之外，是周杰伦和费玉清一起发售的歌曲";

    @Before
    public void setUp() throws Exception {
        BookDO bookDO = new BookDO();
        bookDO.setTitle(title);
        bookDO.setAuthor(author);
        bookDO.setImage(image);
        bookDO.setSummary(summary);
        bookRepository.save(bookDO);
        this.id = bookDO.getId();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findBookByIdAndDeleteTimeIsNull() {
        Optional<BookDO> opt = bookRepository.findBookByIdAndDeleteTimeIsNull(this.id);
        if (opt.isPresent()) {
            assertEquals(opt.get().getTitle(), title);
        } else {
            assertNotNull(opt.get());
        }
    }

    @Test
    public void softFindById() {
        Optional<BookDO> book = bookRepository.softFindById(this.id);
        if (book.isPresent()) {
            assertEquals(book.get().getTitle(), title);
        } else {
            assertNotNull(book.get());
        }
    }

    @Test
    public void findBookByTitleLikeAndDeleteTimeIsNull() {
        Optional<BookDO> opt = bookRepository.findBookByTitleLikeAndDeleteTimeIsNull("%千里%");
        if (opt.isPresent()) {
            assertEquals(opt.get().getTitle(), title);
        } else {
            assertNotNull(opt.get());
        }
    }

    @Test
    public void softFindAll() {
        Iterable<BookDO> books = bookRepository.softFindAll();
        books.forEach(book -> {
            assertNotNull(book.getTitle());
        });
    }

    @Test
    public void softCount() {
        long count = bookRepository.softCount();
        assertTrue(count > 0);
    }

}