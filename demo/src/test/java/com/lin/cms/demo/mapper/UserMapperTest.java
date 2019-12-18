package com.lin.cms.demo.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lin.cms.demo.common.mybatis.Page;
import com.lin.cms.demo.model.GroupDO;
import com.lin.cms.demo.model.UserDO;
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
public class UserMapperTest {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GroupMapper groupMapper;


    private String email = "13129982604@qq.com";
    private String password = "123456";
    private Long groupId;
    private String nickname = "pedro-test";

    private Long userId;

    @Before
    public void setUp() throws Exception {
        GroupDO groupDO = new GroupDO();
        groupDO.setName("分组而已啦！");
        groupDO.setInfo("信息是个傻子吧！");
        groupMapper.insert(groupDO);
        this.groupId = groupDO.getId();

        UserDO userDO = new UserDO();
        userDO.setEmail(email);
        userDO.setNickname(nickname);
        userMapper.insert(userDO);
        this.userId = userDO.getId();
    }


    @After
    public void tearDown() throws Exception {
    }
}