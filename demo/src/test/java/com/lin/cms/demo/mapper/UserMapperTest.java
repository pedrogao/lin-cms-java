package com.lin.cms.demo.mapper;

import com.lin.cms.demo.model.GroupDO;
import com.lin.cms.demo.model.UserAndGroupNameDO;
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
    private Integer groupId;
    private String nickname = "pedro";

    private Integer userId;

    @Before
    public void setUp() throws Exception {
        GroupDO groupDO = new GroupDO();
        groupDO.setName("分组而已啦！");
        groupDO.setInfo("信息是个傻子吧！");
        groupMapper.insertSelective(groupDO);
        this.groupId = groupDO.getId();

        UserDO userDO = new UserDO();
        userDO.setEmail(email);
        userDO.setPasswordEncrypt(password);
        userDO.setGroupId(groupId);
        userDO.setNickname(nickname);
        userMapper.insertSelective(userDO);
        this.userId = userDO.getId();
    }


    @Test
    public void testFindUsersAndGroupName() {
        List<UserAndGroupNameDO> usersAndGroupName = userMapper.findUsersAndGroupName(groupId, 0, 10);
        assertTrue(usersAndGroupName.size() > 0);
    }

    @Test
    public void testGetCommonUsersCount() {
        Integer count = userMapper.getCommonUsersCount(groupId);
        assertTrue(count > 0);
    }

    @Test
    public void testFindOneUserByIdAndDeleteTime() {
        UserDO user = userMapper.findOneUserByIdAndDeleteTime(userId);
        assertEquals(user.getNickname(), nickname);
    }

    @Test
    public void testSoftDeleteById() {
        userMapper.softDeleteById(userId);
        UserDO user = userMapper.findOneUserByIdAndDeleteTime(userId);
        assertEquals(user, null);
    }

    @Test
    public void testFindOneUserByEmailAndDeleteTime() {
        UserDO user = userMapper.findOneUserByEmailAndDeleteTime(email);
        assertEquals(user.getNickname(), nickname);
    }

    @Test
    public void testFindOneUserByGroupId() {
        UserDO user = userMapper.findOneUserByGroupId(groupId);
        assertEquals(user.getNickname(), nickname);
    }


    @After
    public void tearDown() throws Exception {
    }
}