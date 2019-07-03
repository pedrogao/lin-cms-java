package com.lin.cms.demo.service.impl;

import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.mapper.LogMapper;
import com.lin.cms.demo.model.LogDO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // 数据操作后回滚
@Rollback
public class LogServiceImplTest {

    @Autowired
    private LogServiceImpl logService;

    @Autowired
    private LogMapper logMapper;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getLogs() {
        String message = "put your face to the light!";
        String authority = "查看lin的信息";
        Integer userId = 100;
        String userName = "pedro";
        String method = "GET";
        String path = "/";
        Integer status = 200;
        logService.createOneLog(message, authority, userId, userName, method, path, status);

        PageResult logs = logService.getLogs(0, 10, null, null, null);
        assertTrue(logs.getTotalNums() > 0);
    }

    @Test
    public void searchLogs() {
        String message = "put your face to the light!";
        String authority = "查看lin的信息";
        Integer userId = 100;
        String userName = "pedro";
        String method = "GET";
        String path = "/";
        Integer status = 200;
        logService.createOneLog(message, authority, userId, userName, method, path, status);

        PageResult logs = logService.searchLogs(0, 10, null, "put", null, null);
        assertTrue(logs.getTotalNums() > 0);
    }

    @Test
    public void getUserNames() {
        String message = "put your face to the light!";
        String authority = "查看lin的信息";
        Integer userId = 100;
        String userName = "pedro";
        String method = "GET";
        String path = "/";
        Integer status = 200;
        logService.createOneLog(message, authority, userId, userName, method, path, status);

        List<String> names = logService.getUserNames(0, 10);
        assertTrue(names.size() > 0);
    }

    @Test
    public void createOneLog() {
        String message = "put your face to the light!";
        String authority = "查看lin的信息";
        Integer userId = 100;
        String userName = "pedro";
        String method = "GET";
        String path = "/";
        Integer status = 200;
        logService.createOneLog(message, authority, userId, userName, method, path, status);

        LogDO condition = new LogDO();
        condition.setMessage(message);
        LogDO logDO = logMapper.selectOne(condition);

        assertEquals(logDO.getAuthority(), authority);
    }
}