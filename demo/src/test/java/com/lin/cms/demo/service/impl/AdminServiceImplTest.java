package com.lin.cms.demo.service.impl;

import com.lin.cms.core.exception.Forbidden;
import com.lin.cms.demo.mapper.AuthMapper;
import com.lin.cms.demo.mapper.GroupMapper;
import com.lin.cms.demo.validators.admin.NewGroupValidator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // 数据操作后回滚
@Rollback
public class AdminServiceImplTest {

    @Autowired
    private AdminServiceImpl adminService;

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getUsers() {
    }

    @Test
    public void changeUserPassword() {
    }

    @Test
    public void deleteUser() {
    }

    @Test
    public void updateUserInfo() {
    }

    @Test
    public void getGroups() {
    }

    @Test
    public void getGroup() {
    }

    @Test
    public void createGroup() throws Forbidden {
        NewGroupValidator validator = new NewGroupValidator();

        validator.setName("linger");
        validator.setInfo("linger is a finger");
        List<String> auths = new ArrayList<>();
        auths.add("查询自己信息");
        auths.add("查询所有日志");
        auths.add("搜索日志");
        auths.add("查询日志记录的用户");
        validator.setAuths(auths);

        adminService.createGroup(validator);

    }

    @Test
    public void updateGroup() {
    }

    @Test
    public void deleteGroup() {
    }

    @Test
    public void dispatchAuth() {
    }

    @Test
    public void dispatchAuths() {
    }

    @Test
    public void removeAuths() {
    }
}