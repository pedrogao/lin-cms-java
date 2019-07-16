package com.lin.cms.demo.repository;

import com.lin.cms.demo.mapper.AuthMapper;
import com.lin.cms.demo.mapper.GroupMapper;
import com.lin.cms.demo.model.AuthDO;
import com.lin.cms.demo.model.GroupDO;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // 数据操作后回滚
@Rollback
public class GroupRepositoryTest {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private AuthRepository authRepository;

    private Integer groupId;
    private String module = "信息";
    private String auth = "查看lin的信息";

    private String name = "千里之外";
    private String info = "千里之外是个啥";

    @Before
    public void setUp() throws Exception {
        GroupDO groupDO = new GroupDO();
        groupDO.setName(name);
        groupDO.setInfo(info);
        groupRepository.save(groupDO);

        this.groupId = groupDO.getId();
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
    public void findTopByName() {
        GroupDO group = groupRepository.findTopByName(name);
        assertEquals(group.getInfo(), info);
    }
}