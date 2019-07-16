package com.lin.cms.demo.repository;

import com.lin.cms.demo.model.AuthDO;
import com.lin.cms.demo.model.SimpleAuthDO;
import com.lin.cms.demo.utils.NativeConvert;
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
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // 数据操作后回滚
@Rollback
public class AuthRepositoryTest {

    @Autowired
    private AuthRepository authRepository;

    private Integer groupId = 100;
    private String module = "信息";
    private String auth = "查看lin的信息";

    @Before
    public void setUp() throws Exception {
        AuthDO authDO = new AuthDO();
        authDO.setGroupId(groupId);
        authDO.setModule(module);
        authDO.setAuth(auth);
        authRepository.save(authDO);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findByGroupId() {
        List<Map> list = authRepository.findByGroupId(groupId);
        List<SimpleAuthDO> convertMaps = NativeConvert.convertMaps(list, SimpleAuthDO.class);
        assertTrue(convertMaps.size() > 0);
        convertMaps.forEach(one -> {
            assertNotNull(one.getModule());
        });
    }

    @Test
    public void deleteAllByGroupIdAndAuthIn() {
        AuthDO authDO = new AuthDO();
        authDO.setGroupId(groupId);
        authDO.setModule("日志");
        authDO.setAuth("查看所有日志用户");
        authRepository.save(authDO);

        List auths = new ArrayList();
        auths.add(auth);
        authRepository.deleteAllByGroupIdAndAuthIsIn(groupId, auths);

        List<Map> maps = authRepository.findByGroupId(groupId);
        assertTrue(maps.size() > 0);
    }
}