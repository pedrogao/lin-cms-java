package com.lin.cms.demo.v2.service.impl;

import com.lin.cms.demo.dto.user.RegisterDTO;
import com.lin.cms.demo.v2.model.UserDO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
@Rollback
@Slf4j
public class UserServiceImplTest {

    @Autowired
    private UserServiceImpl userService;

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
    }

    @Test
    public void changeUserPassword() {
    }

    @Test
    public void getUserPermissions() {
    }

    @Test
    public void findByUsername() {
        UserDO user = userService.findByUsername("super");
        log.info("user:{}", user);
        assertEquals(user.getUsername(), "super");
    }
}