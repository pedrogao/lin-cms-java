package com.lin.cms.demo.mapper;

import com.lin.cms.demo.model.AuthDO;
import com.lin.cms.demo.model.SimpleAuthDO;
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

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // 数据操作后回滚
@Rollback
public class AuthMapperTest {


    @Autowired
    private AuthMapper authMapper;

    private Integer groupId = 100;
    private String module = "信息";
    private String auth = "查看lin的信息";

    @Before
    public void setUp() throws Exception {
        // mock数据
        AuthDO authDO = new AuthDO();
        authDO.setGroupId(groupId);
        authDO.setModule(module);
        authDO.setAuth(auth);
        authMapper.insert(authDO);
    }

    @Test
    public void testSelectOneByGroupIdAndAuthAndModule() {
        AuthDO authDO = authMapper.selectOneByGroupIdAndAuthAndModule(groupId, auth, module);
        assertEquals(authDO.getGroupId(), groupId);
        assertEquals(authDO.getAuth(), auth);
    }

    @Test
    public void testFindByGroupId() {
        List<SimpleAuthDO> auths = authMapper.findByGroupId(groupId);
        SimpleAuthDO simpleAuthDO = new SimpleAuthDO();
        simpleAuthDO.setAuth(auth);
        simpleAuthDO.setModule(module);
        List<SimpleAuthDO> authcopy = new ArrayList();
        authcopy.add(simpleAuthDO);
        assertEquals(auths, authcopy);
    }

    @Test
    public void testDeleteByGroupId() {
        authMapper.deleteByGroupId(groupId);
        AuthDO authDO = authMapper.selectByPrimaryKey(groupId);
        assertNull(authDO);
    }

    @Test
    public void testDeleteByGroupIdAndInAuths() {
        List<String> auths = new ArrayList<String>();
        auths.add(auth);
        authMapper.deleteByGroupIdAndInAuths(groupId, auths);

        AuthDO authDO = authMapper.selectByPrimaryKey(groupId);
        assertNull(authDO);
    }

    @Test
    public void testFindOneByGroupIdAndAuth() {
        AuthDO one = authMapper.findOneByGroupIdAndAuth(groupId, auth);
        assertEquals(one.getModule(), module);
    }


    @After
    public void tearDown() throws Exception {
    }
}