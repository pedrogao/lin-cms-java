package com.lin.cms.demo.api.cms;

import com.alibaba.fastjson.JSON;
import com.lin.cms.demo.mapper.LogMapper;
import com.lin.cms.demo.model.LogDO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static org.junit.Assert.*;

import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional // 数据操作后回滚
@Rollback
@AutoConfigureMockMvc
public class LogControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private LogMapper logMapper;

    private Date start = new Date();
    private String authority = "查看lin的信息";
    private String message = "就是个瓜皮";
    private String method = "GET";
    private String path = "/";
    private Integer statusCode = 200;
    private Integer userId = 1;
    private String userName = "pppp";

    @Before
    public void setUp() throws Exception {
        LogDO logDO = new LogDO();
        logDO.setAuthority(authority);
        logDO.setMessage(message);
        logDO.setMethod(method);
        logDO.setPath(path);
        logDO.setStatusCode(statusCode);
        logDO.setUserId(userId);
        logDO.setUserName(userName);
        logDO.setTime(start);
        logMapper.insertSelective(logDO);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getLogs() throws Exception {
        mvc.perform(get("/cms/log/").param("name", "pppp")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.total_nums").isNumber());

    }

    @Test
    public void searchLogs() throws Exception {
        mvc.perform(get("/cms/log/search")
                .param("name", "pppp").param("keyword", "瓜皮")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.total_nums").isNumber());
    }

    @Test
    public void getUsers() throws Exception {
        mvc.perform(get("/cms/log/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$").isArray());
    }
}