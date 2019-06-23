package com.lin.cms.demo.service.impl;

import com.lin.cms.core.exception.Forbidden;
import com.lin.cms.demo.validators.admin.NewGroupValidator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminServiceImplTest {

    @Autowired
    private AdminServiceImpl adminService;

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
}