package com.lin.cms.demo.service.impl;

import com.lin.cms.demo.service.UserService;
import com.lin.cms.exception.ForbiddenException;
import com.lin.cms.demo.dto.user.AvatarUpdateDTO;
import com.lin.cms.demo.dto.user.ChangePasswordDTO;
import com.lin.cms.demo.dto.user.RegisterDTO;
import com.lin.cms.demo.dto.user.UpdateInfoDTO;
import com.lin.cms.demo.mapper.UserMapper;
import com.lin.cms.demo.model.UserDO;
import com.lin.cms.demo.common.LocalUserLegacy;
import com.lin.cms.exception.ParameterException;
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
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    private String email = "13129982604@qq.com";
    private String password = "123456";
    private Long groupId = 100L;
    private String nickname = "pedro";

    private Long userId;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void createUser() throws ForbiddenException {
        RegisterDTO validator = new RegisterDTO();
        // validator.setNickname(nickname);
        validator.setPassword(password);
        validator.setConfirmPassword(password);
        validator.setEmail(email);
        validator.setGroupId(groupId);
        userService.createUser(validator);

        UserDO user = userMapper.findOneUserByEmailAndDeleteTime(email);
        assertEquals(user.getNickname(), nickname);

    }

    @Test
    public void updateUser() {
        RegisterDTO validator = new RegisterDTO();
        // validator.setNickname(nickname);
        validator.setPassword(password);
        validator.setConfirmPassword(password);
        validator.setEmail(email);
        validator.setGroupId(groupId);
        userService.createUser(validator);
        UserDO localUser = userMapper.findOneUserByEmailAndDeleteTime(email);
        LocalUserLegacy.setLocalUser(localUser);

        UpdateInfoDTO dto = new UpdateInfoDTO();
        dto.setEmail("11111111111@qq.com");
        userService.updateUser(dto);

        UserDO user = userMapper.findOneUserByEmailAndDeleteTime("11111111111@qq.com");
        assertNotNull(user);
    }

    @Test
    public void changePassword() {
        RegisterDTO validator = new RegisterDTO();
        // validator.setNickname(nickname);
        validator.setPassword(password);
        validator.setConfirmPassword(password);
        validator.setEmail(email);
        validator.setGroupId(groupId);
        userService.createUser(validator);
        UserDO localUser = userMapper.findOneUserByEmailAndDeleteTime(email);
        LocalUserLegacy.setLocalUser(localUser);

        ChangePasswordDTO dto = new ChangePasswordDTO();
        dto.setNewPassword("147258");
        dto.setOldPassword(password);
        dto.setConfirmPassword("147258");

        userService.changePassword(dto);
        UserDO user = userMapper.findOneUserByEmailAndDeleteTime(email);
        assertTrue(user.verify("147258"));
    }

    @Test
    public void updateAvatar() throws ForbiddenException {
        RegisterDTO validator = new RegisterDTO();
        // validator.setNickname(nickname);
        validator.setPassword(password);
        validator.setConfirmPassword(password);
        validator.setEmail(email);
        validator.setGroupId(groupId);
        userService.createUser(validator);
        UserDO localUser = userMapper.findOneUserByEmailAndDeleteTime(email);
        LocalUserLegacy.setLocalUser(localUser);

        AvatarUpdateDTO dto = new AvatarUpdateDTO();
        dto.setAvatar("oohoihihiiiuvuiv.jpg");
        userService.updateAvatar(dto);

        UserDO user = userMapper.findOneUserByEmailAndDeleteTime(email);
        assertEquals(user.getAvatar(), "oohoihihiiiuvuiv.jpg");
    }
}