package com.lin.cms.demo.api.v1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // 数据操作后回滚
@Rollback
public class BookControllerTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void getBook() {
    }

    @Test
    public void getBooks() {
    }

    @Test
    public void searchBook() {
    }

    @Test
    public void createBook() {
    }

    @Test
    public void updateBook() {
    }

    @Test
    public void deleteBook() {
    }


    @After
    public void tearDown() throws Exception {
    }
}