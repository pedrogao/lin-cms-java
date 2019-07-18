package com.lin.cms.demo.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lin.cms.demo.common.page.Page;
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

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // 数据操作后回滚
@Rollback
public class LogMapperTest {


    @Autowired
    private LogMapper logMapper;

    private Date start = new Date();
    private String authority = "查看lin的信息";
    private String message = "就是个瓜皮";
    private String method = "GET";
    private String path = "/";
    private Integer statusCode = 200;
    private Integer userId = 1;
    private String userName = "super";

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
        long ll = start.getTime() - 500000;
        // start.setTime(ll);
        start = new Date(ll);
        logMapper.insert(logDO);
    }

    @Test
    public void testFindLogsByUsernameAndRange() {
        Date now = new Date();
        Page page = new Page(0, 10);
        IPage<LogDO> iPage = logMapper.findLogsByUsernameAndRange(page, userName, start, now);
        List<LogDO> logs = iPage.getRecords();
        assertTrue(logs.size() > 0);
    }

    @Test
    public void testFindLogsByUsernameAndRange1() {
        long changed = start.getTime();
        Date ch = new Date(changed - 1000);
        Date ch1 = new Date(changed - 2000);
        Page page = new Page(1, 10);
        IPage<LogDO> iPage = logMapper.findLogsByUsernameAndRange(page, userName, ch1, ch);
        List<LogDO> logs = iPage.getRecords();
        assertTrue(logs.size() == 0);
    }

    @Test
    public void testCountLogsByUsernameAndRange() {
        Date now = new Date();
        Integer count = logMapper.countLogsByUsernameAndRange(userName, start, now);
        assertTrue(count > 0);
    }

    @Test
    public void testSearchLogsByUsernameAndKeywordAndRange() {
        Date now = new Date();
        Page page = new Page(0, 10);
        IPage<LogDO> iPage = logMapper.searchLogsByUsernameAndKeywordAndRange(page, userName, "瓜皮", start, now);
        List<LogDO> logs = iPage.getRecords();
        assertTrue(logs.size() > 0);
    }

    @Test
    public void testCountLogsByUsernameAndKeywordAndRange() {
        Date now = new Date();
        Integer count = logMapper.countLogsByUsernameAndKeywordAndRange(userName, "瓜皮", start, now);
        assertTrue(count > 0);
    }

    @Test
    public void testGetUserNames() {
        Page page = new Page(0, 10);
        IPage iPage = logMapper.getUserNames(page);
        List<String> names = iPage.getRecords();
        assertTrue(names.size() > 0);
    }

    @After
    public void tearDown() throws Exception {
    }
}