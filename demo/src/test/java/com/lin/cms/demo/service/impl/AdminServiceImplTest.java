package com.lin.cms.demo.service.impl;

import com.lin.cms.demo.service.AdminService;
import com.lin.cms.exception.ForbiddenException;
import com.lin.cms.exception.NotFoundException;
import com.lin.cms.demo.vo.PageResult;
import com.lin.cms.demo.bo.GroupAuthsBO;
import com.lin.cms.demo.dto.admin.*;
import com.lin.cms.demo.mapper.AuthMapper;
import com.lin.cms.demo.mapper.GroupMapper;
import com.lin.cms.demo.mapper.UserMapper;
import com.lin.cms.demo.model.GroupDO;
import com.lin.cms.demo.model.SimpleAuthDO;
import com.lin.cms.demo.model.UserDO;
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

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional // 数据操作后回滚
@Rollback
public class AdminServiceImplTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private UserMapper userMapper;

    private String email = "13129982604@qq.com";
    private String password = "123456";
    private Long groupId;
    private String nickname = "pedro--test";

    private Long userId;

    private String module = "信息";
    private String auth = "查看lin的信息";

    private String name = "千里之外";
    private String info = "千里之外是个啥";

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getUsers() {
        UserDO userDO = new UserDO();
        userDO.setNickname(nickname);
        userDO.setPasswordEncrypt(password);
        userDO.setEmail(email);
        userDO.setGroupId(100L);
        userMapper.insert(userDO);
        this.userId = userDO.getId();

        PageResult users = adminService.getUsers(100L, 10L, 0L);
        assertTrue(users.getCount() == 1);

        PageResult users1 = adminService.getUsers(null, 10L, 0L);
        assertTrue(users1.getCount() == 1);
    }

    @Test
    public void changeUserPassword() throws NotFoundException {
        UserDO userDO = new UserDO();
        userDO.setNickname(nickname);
        userDO.setPasswordEncrypt(password);
        userDO.setEmail(email);
        userMapper.insert(userDO);
        this.userId = userDO.getId();

        String newPassword = "111111111";

        ResetPasswordDTO dto = new ResetPasswordDTO();
        dto.setNewPassword(newPassword);
        dto.setConfirmPassword(newPassword);
        adminService.changeUserPassword(this.userId, dto);

        UserDO aDo = userMapper.selectById(this.userId);
        boolean valid = aDo.verify(newPassword);
        assertTrue(valid);
    }

    @Test
    public void deleteUser() throws NotFoundException {
        UserDO userDO = new UserDO();
        userDO.setNickname(nickname);
        userDO.setPasswordEncrypt(password);
        userDO.setEmail(email);
        userMapper.insert(userDO);
        this.userId = userDO.getId();

        adminService.deleteUser(this.userId);

        UserDO user = userMapper.findOneUserByIdAndDeleteTime(this.userId);
        assertNull(user);
    }

    @Test
    public void updateUserInfo() throws NotFoundException, ParameterException {
        UserDO userDO = new UserDO();
        userDO.setNickname(nickname);
        userDO.setPasswordEncrypt(password);
        userDO.setEmail(email);
        userMapper.insert(userDO);
        this.userId = userDO.getId();

        UpdateUserInfoDTO dto = new UpdateUserInfoDTO();
        dto.setGroupId(1L);
        dto.setEmail("23129982604@qq.com");

        adminService.updateUserInfo(this.userId, dto);

        UserDO user = userMapper.findOneUserByIdAndDeleteTime(userId);
        assertEquals(user.getGroupId(), new Integer(1));
        assertEquals(user.getEmail(), "23129982604@qq.com");
    }

    @Test
    public void getGroups() {
        GroupDO groupDO = new GroupDO();
        groupDO.setName(name);
        groupDO.setInfo(info);
        groupMapper.insert(groupDO);

        PageResult groups = adminService.getGroups(0L, 10L);

        assertTrue(groups.getCount() > 0);
    }

    @Test
    public void getGroup() {
        GroupDO groupDO = new GroupDO();
        groupDO.setName(name);
        groupDO.setInfo(info);
        groupMapper.insert(groupDO);

        GroupAuthsBO authsBO = adminService.getGroup(groupDO.getId());
        assertEquals(authsBO.getName(), name);
        assertTrue(authsBO.getPermissions().size() == 0);
    }

    @Test
    public void createGroup() throws ForbiddenException {
        NewGroupDTO validator = new NewGroupDTO();

        validator.setName("logger");
        validator.setInfo("logger is a finger");
        List<String> auths = new ArrayList<>();
        auths.add("查询自己信息");
        auths.add("查询所有日志");
        auths.add("搜索日志");
        auths.add("查询日志记录的用户");
        // validator.setAuths(auths);
        adminService.createGroup(validator);


        GroupDO groupDO = groupMapper.findOneByName("logger");
        assertEquals(groupDO.getInfo(), "logger is a finger");

        List<SimpleAuthDO> authDOS = authMapper.findByGroupId(groupDO.getId());
        assertTrue(authDOS.size() > 1);
    }

    @Test
    public void updateGroup() throws NotFoundException {
        GroupDO groupDO = new GroupDO();
        groupDO.setName(name);
        groupDO.setInfo(info);
        groupMapper.insert(groupDO);

        String newName = "我就是一个新的分组名";
        UpdateGroupDTO dto = new UpdateGroupDTO();
        dto.setName(newName);
        adminService.updateGroup(groupDO.getId(), dto);

        GroupDO aDo = groupMapper.selectById(groupDO.getId());
        assertEquals(aDo.getName(), newName);
    }

    @Test
    public void deleteGroup() throws NotFoundException, ForbiddenException {
        GroupDO groupDO = new GroupDO();
        groupDO.setName(name);
        groupDO.setInfo(info);
        groupMapper.insert(groupDO);

        adminService.deleteGroup(groupDO.getId());

        GroupDO aDo = groupMapper.selectById(groupDO.getId());
        assertNull(aDo);
    }

    @Test
    public void dispatchAuth() throws NotFoundException, ForbiddenException {
        GroupDO groupDO = new GroupDO();
        groupDO.setName(name);
        groupDO.setInfo(info);
        groupMapper.insert(groupDO);

        DispatchPermissionDTO dto = new DispatchPermissionDTO();
        dto.setGroupId(groupDO.getId());
        // dto.setAuth("查询自己信息");

        adminService.dispatchAuth(dto);

        List<SimpleAuthDO> dos = authMapper.findByGroupId(groupDO.getId());
        assertTrue(dos.size() > 0);
    }

    @Test
    public void dispatchAuths() throws NotFoundException {
        GroupDO groupDO = new GroupDO();
        groupDO.setName(name);
        groupDO.setInfo(info);
        groupMapper.insert(groupDO);

        DispatchPermissionsDTO dto = new DispatchPermissionsDTO();
        dto.setGroupId(groupDO.getId());

        List<String> auths = new ArrayList<>();
        auths.add("查询自己信息");
        auths.add("查询所有日志");
        auths.add("搜索日志");
        auths.add("查询日志记录的用户");
        // dto.setAuths(auths);

        adminService.dispatchAuths(dto);

        List<SimpleAuthDO> dos = authMapper.findByGroupId(groupDO.getId());
        assertTrue(dos.size() > 1);
    }

    @Test
    public void removeAuths() throws NotFoundException, ForbiddenException {
        GroupDO groupDO = new GroupDO();
        groupDO.setName(name);
        groupDO.setInfo(info);
        groupMapper.insert(groupDO);

        DispatchPermissionDTO dto = new DispatchPermissionDTO();
        dto.setGroupId(groupDO.getId());
        // dto.setAuth("查询自己信息");

        adminService.dispatchAuth(dto);

        RemovePermissionsDTO validator = new RemovePermissionsDTO();
        validator.setGroupId(groupDO.getId());

        List<String> auths = new ArrayList<>();
        auths.add("查询自己信息");
        // validator.setAuths(auths);

        adminService.removeAuths(validator);

        List<SimpleAuthDO> dos = authMapper.findByGroupId(groupDO.getId());
        assertTrue(dos.size() == 0);
    }
}