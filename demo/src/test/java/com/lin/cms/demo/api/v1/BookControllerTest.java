package com.lin.cms.demo.api.v1;

import com.alibaba.fastjson.JSON;
import com.lin.cms.demo.dto.book.CreateOrUpdateBookDTO;
import com.lin.cms.demo.mapper.BookMapper;
import com.lin.cms.demo.model.BookDO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional // 数据操作后回滚
@Rollback
@AutoConfigureMockMvc
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

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

    @Test
    public void getBook() throws Exception {
        BookDO bookDO = new BookDO();
        bookDO.setTitle(title);
        bookDO.setAuthor(author);
        bookDO.setImage(image);
        bookDO.setSummary(summary);
        // bookMapper.insertSelective(bookDO);
        bookMapper.insert(bookDO);

        this.id = bookDO.getId();
        this.mvc.perform(get("/v1/book/" + this.id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.title").value(title));
    }

    @Test
    public void getBooks() throws Exception {
        BookDO bookDO = new BookDO();
        bookDO.setTitle(title);
        bookDO.setAuthor(author);
        bookDO.setImage(image);
        bookDO.setSummary(summary);
        // bookMapper.insertSelective(bookDO);
        bookMapper.insert(bookDO);
        this.id = bookDO.getId();

        mvc.perform(get("/v1/book/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$").isArray())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$[0].title").value(title));
    }

    @Test
    public void searchBook() throws Exception {
        BookDO bookDO = new BookDO();
        bookDO.setTitle(title);
        bookDO.setAuthor(author);
        bookDO.setImage(image);
        bookDO.setSummary(summary);
        // bookMapper.insertSelective(bookDO);
        bookMapper.insert(bookDO);
        this.id = bookDO.getId();

        mvc.perform(get("/v1/book/search/one?q=千里"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.title").value(title));
    }

    @Test
    public void createBook() throws Exception {
        CreateOrUpdateBookDTO validator = new CreateOrUpdateBookDTO();
        validator.setAuthor(author);
        validator.setImage(image);
        validator.setSummary(summary);
        validator.setTitle(title);

        mvc.perform(post("/v1/book/")
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONBytes(validator)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.msg").value("新建图书成功"));
    }

    @Test
    public void updateBook() throws Exception {
        BookDO bookDO = new BookDO();
        bookDO.setTitle(title);
        bookDO.setAuthor(author);
        bookDO.setImage(image);
        bookDO.setSummary(summary);
        // bookMapper.insertSelective(bookDO);
        bookMapper.insert(bookDO);
        this.id = bookDO.getId();

        CreateOrUpdateBookDTO validator = new CreateOrUpdateBookDTO();
        validator.setAuthor(author);
        validator.setImage(image);
        validator.setSummary(summary);
        validator.setTitle(title + "lol");

        mvc.perform(put("/v1/book/" + this.id)
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONBytes(validator)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.msg").value("更新图书成功"));
    }

    @Test
    public void deleteBook() throws Exception {
        BookDO bookDO = new BookDO();
        bookDO.setTitle(title);
        bookDO.setAuthor(author);
        bookDO.setImage(image);
        bookDO.setSummary(summary);
        bookMapper.insert(bookDO);
        // bookMapper.insertSelective(bookDO);
        this.id = bookDO.getId();

        mvc.perform(delete("/v1/book/" + this.id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.msg").value("删除图书成功"));
    }


    @After
    public void tearDown() throws Exception {
    }
}