package com.lin.cms.demo.repository;

import com.lin.cms.demo.model.GroupDO;
import com.lin.cms.demo.model.UserAndGroupNameDO;
import com.lin.cms.demo.model.UserDO;
import com.lin.cms.demo.utils.NativeConvert;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // 数据操作后回滚
@Rollback
@Slf4j
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    private String email = "13129982604@qq.com";
    private String password = "123456";
    private Integer groupId;
    private String nickname = "jerry";

    private Integer userId;

    @Before
    public void setUp() throws Exception {
        GroupDO groupDO = new GroupDO();
        groupDO.setName("分组而已啦！");
        groupDO.setInfo("信息是个傻子吧！");
        groupDO = groupRepository.save(groupDO);
        this.groupId = groupDO.getId();

        UserDO userDO = new UserDO();
        userDO.setEmail(email);
        userDO.setPasswordEncrypt(password);
        userDO.setGroupId(groupId);
        userDO.setNickname(nickname);
        userDO = userRepository.save(userDO);
        this.userId = userDO.getId();
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void findUsersAndGroupName() {
        Pageable pageable = PageRequest.of(1, 10);
        Page<Map> page = userRepository.findUsersAndGroupName(pageable);

        assertTrue(page.getTotalElements() > 0);

        List<Map> usersAndGroupName = page.getContent();

        List<UserAndGroupNameDO> converts = NativeConvert.convertMaps(usersAndGroupName, UserAndGroupNameDO.class);
        converts.forEach(one -> {
            String nickname = one.getNickname();
            log.info(nickname);
            assertNotNull(one);
        });
    }


    @Test
    public void findUsersAndGroupName1() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Map> page = userRepository.findUsersAndGroupName(groupId, pageable);

        assertTrue(page.getTotalElements() > 0);

        List<Map> usersAndGroupName = page.getContent();

        List<UserAndGroupNameDO> converts = NativeConvert.convertMaps(usersAndGroupName, UserAndGroupNameDO.class);
        converts.forEach(one -> {
            String nickname = one.getNickname();
            log.info(nickname);
            assertNotNull(one);
        });
    }

    @Test
    public void findByIdAndDeleteTimeIsNull() {
        UserDO userDO = userRepository.findByIdAndDeleteTimeIsNull(userId);
        assertNotNull(userDO);
        assertEquals(userDO.getNickname(), nickname);
    }

    @Test
    public void findByEmailAndDeleteTimeIsNull() {
        UserDO userDO = userRepository.findByEmailAndDeleteTimeIsNull(email);
        assertNotNull(userDO);
        assertEquals(userDO.getNickname(), nickname);
    }

    @Test
    public void findByGroupIdAndDeleteTimeIsNull() {
        UserDO userDO = new UserDO();
        userDO.setEmail("12988879866@qq.com");
        userDO.setPasswordEncrypt(password);
        userDO.setGroupId(groupId);
        userDO.setNickname("oppppppp");
        userRepository.save(userDO);

        UserDO user = userRepository.findFirstByGroupIdAndDeleteTimeIsNull(groupId);
        assertNotNull(user);
        assertEquals(user.getNickname(), nickname);
    }
}