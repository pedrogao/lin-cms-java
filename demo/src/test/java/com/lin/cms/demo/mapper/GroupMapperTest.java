package com.lin.cms.demo.mapper;

import com.lin.cms.demo.model.AuthDO;
import com.lin.cms.demo.model.GroupAndAuthDO;
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

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // 数据操作后回滚
@Rollback
public class GroupMapperTest {

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private AuthMapper authMapper;

    private Long groupId;
    private String module = "信息";
    private String auth = "查看lin的信息";

    private String name = "千里之外";
    private String info = "千里之外是个啥";

    @Before
    public void setUp() throws Exception {
        GroupDO groupDO = new GroupDO();
        groupDO.setName(name);
        groupDO.setInfo(info);
        groupMapper.insert(groupDO);

        this.groupId = groupDO.getId();
        AuthDO authDO = new AuthDO();
        authDO.setGroupId(groupId);
        authDO.setModule(module);
        authDO.setAuth(auth);
        authMapper.insert(authDO);
    }

    @Test
    public void testSelectOneByGroupIdAndAuthAndModule() {
        List<GroupAndAuthDO> groupsWithAuths = groupMapper.findGroupsWithAuths();
        assertTrue(groupsWithAuths.size() > 0);
    }

    @Test
    public void testGetCount() {
        Integer count = groupMapper.getCount();
        assertTrue(count > 0);
    }

    @Test
    public void testFindOneByName() {
        GroupDO one = groupMapper.findOneByName(name);
        assertEquals(one.getInfo(), info);
    }


    @After
    public void tearDown() throws Exception {
    }
}