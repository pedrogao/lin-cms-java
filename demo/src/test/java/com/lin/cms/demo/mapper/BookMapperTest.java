package com.lin.cms.demo.mapper;

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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // 数据操作后回滚
@Rollback
public class BookMapperTest {

    @Autowired
    private BookMapper bookMapper;

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
        bookMapper.insertSelective(bookDO);
        this.id = bookDO.getId();
    }


    @Test
    public void testGetBookByKeyword() {
        BookDO found = bookMapper.getBookByKeyword("千里");
        assertEquals(found.getTitle(), title);
    }


    @Test
    public void testFindOneByIdAndDeleteTime() {
        BookDO found = bookMapper.findOneByIdAndDeleteTime(this.id);
        assertEquals(found.getTitle(), title);
    }


    @After
    public void tearDown() throws Exception {
    }
}