package com.lin.cms.demo.v2.service.impl;

import com.lin.cms.demo.common.LocalUser;
import com.lin.cms.demo.dto.user.ChangePasswordDTO;
import com.lin.cms.demo.dto.user.RegisterDTO;
import com.lin.cms.demo.dto.user.UpdateInfoDTO;
import com.lin.cms.demo.v2.model.UserDO;
import lombok.extern.slf4j.Slf4j;
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
@Transactional
@Rollback
@Slf4j
public class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserIdentityServiceImpl userIdentityService;

    @Before
    public void setUp() {
        RegisterDTO dto = new RegisterDTO();
        dto.setUsername("pedro");
        dto.setPassword("123456");
        dto.setConfirmPassword("123456");
        log.info("dto: {}", dto);
        UserDO user = userService.createUser(dto);
        log.info("user: {}", user);
        // Mock logined user
        LocalUser.setLocalUser(user);
    }

    @Test
    public void createUser() {
        RegisterDTO dto = new RegisterDTO();
        dto.setUsername("pedro111");
        dto.setPassword("123456");
        dto.setConfirmPassword("123456");
        log.info("dto: {}", dto);
        UserDO user = userService.createUser(dto);
        log.info("user: {}", user);
        assertEquals(user.getUsername(), "pedro111");
        assertNull(user.getEmail());
    }

    @Test
    public void updateUserInfo() {
        UpdateInfoDTO dto = new UpdateInfoDTO();
        dto.setNickname("pedro-gao");
        dto.setUsername("pedro-gao");
        dto.setEmail("1312342604@qq.com");
        userService.updateUserInfo(dto);

        UserDO userDO = userService.findByUsername("pedro-gao");
        assertEquals(userDO.getEmail(), "1312342604@qq.com");

        boolean b = userIdentityService.verifyUsernamePassword(userDO.getId(), "pedro-gao", "123456");
        assertTrue(b);
    }

    @Test
    public void updateUserInfoNoUsername() {
        UpdateInfoDTO dto = new UpdateInfoDTO();
        dto.setNickname("pedro-gao");
        dto.setEmail("1312342604@qq.com");
        userService.updateUserInfo(dto);

        UserDO userDO = userService.findByUsername("pedro");
        assertEquals(userDO.getEmail(), "1312342604@qq.com");
        assertEquals(userDO.getNickname(), "pedro-gao");

        boolean b = userIdentityService.verifyUsernamePassword(userDO.getId(), "pedro", "123456");
        assertTrue(b);
    }

    @Test
    public void changeUserPassword() {
        ChangePasswordDTO dto = new ChangePasswordDTO();
        dto.setNewPassword("147258");
        dto.setConfirmPassword("147258");
        dto.setOldPassword("123456");
        UserDO user = userService.changeUserPassword(dto);

        boolean b = userIdentityService.verifyUsernamePassword(user.getId(), "pedro", "147258");
        assertTrue(b);
    }

    @Test
    public void getUserPermissions() {
    }

    @Test
    public void findByUsername() {
        UserDO user = userService.findByUsername("pedro");
        log.info("user:{}", user);
        assertEquals(user.getUsername(), "pedro");
    }

    @Test
    public void checkUserExistByUsername() {
        boolean b = userService.checkUserExistByUsername("pedro");
        assertTrue(b);
    }


    @Test
    public void checkUserExistById() {
        boolean b = userService.checkUserExistById(100L);
        assertFalse(b);
    }

    @Test
    public void checkUserExistById1() {
        UserDO user = LocalUser.getLocalUser();
        boolean b = userService.checkUserExistById(user.getId());
        assertTrue(b);
    }
}