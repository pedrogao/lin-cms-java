package com.lin.cms.demo.service.impl;

import com.lin.cms.exception.Forbidden;
import com.lin.cms.exception.Parameter;
import com.lin.cms.demo.dto.user.AvatarUpdateDTO;
import com.lin.cms.demo.dto.user.ChangePasswordDTO;
import com.lin.cms.demo.dto.user.RegisterDTO;
import com.lin.cms.demo.dto.user.UpdateInfoDTO;
import com.lin.cms.demo.mapper.UserMapper;
import com.lin.cms.demo.model.UserDO;
import com.lin.cms.demo.utils.LocalUser;
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
public class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserMapper userMapper;

    private String email = "13129982604@qq.com";
    private String password = "123456";
    private Integer groupId = 100;
    private String nickname = "pedro";

    private Integer userId;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createUser() throws Forbidden {
        RegisterDTO validator = new RegisterDTO();
        validator.setNickname(nickname);
        validator.setPassword(password);
        validator.setConfirmPassword(password);
        validator.setEmail(email);
        validator.setGroupId(groupId);
        userService.createUser(validator);

        UserDO user = userMapper.findOneUserByEmailAndDeleteTime(email);
        assertEquals(user.getNickname(), nickname);

    }

    @Test
    public void updateUser() throws Forbidden, Parameter {
        RegisterDTO validator = new RegisterDTO();
        validator.setNickname(nickname);
        validator.setPassword(password);
        validator.setConfirmPassword(password);
        validator.setEmail(email);
        validator.setGroupId(groupId);
        userService.createUser(validator);
        UserDO localUser = userMapper.findOneUserByEmailAndDeleteTime(email);
        LocalUser.setLocalUser(localUser);

        UpdateInfoDTO dto = new UpdateInfoDTO();
        dto.setEmail("11111111111@qq.com");
        userService.updateUser(dto);

        UserDO user = userMapper.findOneUserByEmailAndDeleteTime("11111111111@qq.com");
        assertNotNull(user);
    }

    @Test
    public void changePassword() throws Forbidden, Parameter {
        RegisterDTO validator = new RegisterDTO();
        validator.setNickname(nickname);
        validator.setPassword(password);
        validator.setConfirmPassword(password);
        validator.setEmail(email);
        validator.setGroupId(groupId);
        userService.createUser(validator);
        UserDO localUser = userMapper.findOneUserByEmailAndDeleteTime(email);
        LocalUser.setLocalUser(localUser);

        ChangePasswordDTO dto = new ChangePasswordDTO();
        dto.setNewPassword("147258");
        dto.setOldPassword(password);
        dto.setConfirmPassword("147258");

        userService.changePassword(dto);
        UserDO user = userMapper.findOneUserByEmailAndDeleteTime(email);
        assertTrue(user.verify("147258"));
    }

    @Test
    public void updateAvatar() throws Forbidden {
        RegisterDTO validator = new RegisterDTO();
        validator.setNickname(nickname);
        validator.setPassword(password);
        validator.setConfirmPassword(password);
        validator.setEmail(email);
        validator.setGroupId(groupId);
        userService.createUser(validator);
        UserDO localUser = userMapper.findOneUserByEmailAndDeleteTime(email);
        LocalUser.setLocalUser(localUser);

        AvatarUpdateDTO dto = new AvatarUpdateDTO();
        dto.setAvatar("oohoihihiiiuvuiv.jpg");
        userService.updateAvatar(dto);

        UserDO user = userMapper.findOneUserByEmailAndDeleteTime(email);
        assertEquals(user.getAvatar(), "oohoihihiiiuvuiv.jpg");
    }
}