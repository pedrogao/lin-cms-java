package com.lin.cms.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lin.cms.demo.mapper.LogMapper;
import com.lin.cms.demo.model.LogDO;
import com.lin.cms.demo.vo.PageResult;
import com.lin.cms.demo.service.LogService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback
@ActiveProfiles("test")
public class LogServiceImplTest {

    @Autowired
    private LogService logService;

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
        Long userId = 100L;
        String userName = "pedro";
        String method = "GET";
        String path = "/";
        Integer status = 200;
        logService.createLog(message, authority, userId, userName, method, path, status);

        PageResult logs = logService.getLogs(0L, 10L, null, null, null);
        assertTrue(logs.getCount() > 0);
    }

    @Test
    public void searchLogs() {
        String message = "put your face to the light!";
        String authority = "查看lin的信息";
        Long userId = 100L;
        String userName = "pedro";
        String method = "GET";
        String path = "/";
        Integer status = 200;
        logService.createLog(message, authority, userId, userName, method, path, status);

        PageResult logs = logService.searchLogs(0L, 10L, null, "put", null, null);
        assertTrue(logs.getCount() > 0);
    }

    @Test
    public void getUserNames() {
        String message = "put your face to the light!";
        String authority = "查看lin的信息";
        Long userId = 100L;
        String userName = "pedro";
        String method = "GET";
        String path = "/";
        Integer status = 200;
        logService.createLog(message, authority, userId, userName, method, path, status);

        List<String> names = logService.getUserNames(0L, 10L);
        assertTrue(names.size() > 0);
    }

    @Test
    public void createOneLog() {
        String message = "put your face to the light!";
        String permission = "查看lin的信息";
        Long userId = 100L;
        String userName = "pedro";
        String method = "GET";
        String path = "/";
        Integer status = 200;
        logService.createLog(message, permission, userId, userName, method, path, status);

        QueryWrapper<LogDO> condition = new QueryWrapper<>();
        condition.eq("message", message);
        LogDO logDO = logMapper.selectOne(condition);

        assertEquals(logDO.getPermission(), permission);
    }
}