package com.lin.cms.demo.controller.cms;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.google.common.base.Strings;
import com.lin.cms.demo.dto.admin.*;
import com.lin.cms.demo.mapper.AuthMapper;
import com.lin.cms.demo.v2.mapper.*;
import com.lin.cms.demo.model.AuthDO;
import com.lin.cms.demo.v2.model.*;
import com.lin.cms.demo.v2.service.impl.UserIdentityServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Rollback
@AutoConfigureMockMvc
@Slf4j
public class AdminControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserGroupMapper userGroupMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private GroupPermissionMapper groupPermissionMapper;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private UserIdentityServiceImpl userIdentityService;

    @Autowired
    private AuthMapper authMapper;

    private String email = "13129982604@qq.com";
    private String password = "123456";
    private String nickname = "pedro";

    private Long groupId;
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
    public void getAllPermissions() throws Exception {
        mvc.perform(get("/cms/admin/permission")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.用户").isNotEmpty());
    }

    @Test
    public void getUsers() throws Exception {
        mvc.perform(get("/cms/admin/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.total").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.count").value(10));
    }

    @Test
    public void getUsers1() throws Exception {
        String email = "13129982604@qq.com";
        String username = "pedro大大";
        String nickname = "pedro大大";
        UserDO user = UserDO.builder().username(username).nickname(nickname).email(email).build();
        userMapper.insert(user);


        String name = "千里之外";
        String info = "千里之外是个啥";
        GroupDO group = GroupDO.builder().name(name).info(info).build();
        groupMapper.insert(group);

        userGroupMapper.insert(new UserGroupDO(user.getId(), group.getId()));

        String module = "信息";
        String permissionName = "查看lin的信息";

        PermissionDO permission = PermissionDO.builder().name(permissionName).module(module).build();
        permissionMapper.insert(permission);

        groupPermissionMapper.insert(new GroupPermissionDO(group.getId(), permission.getId()));

        mvc.perform(get("/cms/admin/users")
                .param("group_id", group.getId() + "")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.total").isNumber())
                .andExpect(MockMvcResultMatchers.jsonPath("$.items").isArray());
    }

    @Test
    public void changeUserPassword() throws Exception {
        String email = "13129982604@qq.com";
        String username = "pedro大大";
        String nickname = "pedro大大";
        UserDO user = UserDO.builder().username(username).nickname(nickname).email(email).build();
        userMapper.insert(user);


        String name = "千里之外";
        String info = "千里之外是个啥";
        GroupDO group = GroupDO.builder().name(name).info(info).build();
        groupMapper.insert(group);

        userGroupMapper.insert(new UserGroupDO(user.getId(), group.getId()));
        userIdentityService.createUsernamePasswordIdentity(user.getId(), username, "123456");

        String newPassword = "111111111";

        ResetPasswordDTO dto = new ResetPasswordDTO();
        dto.setNewPassword(newPassword);
        dto.setConfirmPassword(newPassword);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        String content = mapper.writeValueAsString(dto);

        mvc.perform(put(String.format("/cms/admin/%s/password", user.getId()))
                .contentType(MediaType.APPLICATION_JSON).content(content))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("密码修改成功"));

        boolean b = userIdentityService.verifyUsernamePassword(user.getId(), username, newPassword);
        assertTrue(b);
    }

    @Test
    public void deleteUser() throws Exception {
        String email = "13129982604@qq.com";
        String username = "pedro大大";
        String nickname = "pedro大大";
        UserDO user = UserDO.builder().username(username).nickname(nickname).email(email).build();
        userMapper.insert(user);


        String name = "千里之外";
        String info = "千里之外是个啥";
        GroupDO group = GroupDO.builder().name(name).info(info).build();
        groupMapper.insert(group);

        userGroupMapper.insert(new UserGroupDO(user.getId(), group.getId()));

        mvc.perform(delete("/cms/admin/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("删除用户成功"));

        UserDO hit = userMapper.selectById(user.getId());
        assertNull(hit);
    }

    @Test
    public void updateUser() throws Exception {
        String email = "13129982604@qq.com";
        String username = "pedro大大";
        String nickname = "pedro大大";
        UserDO user = UserDO.builder().username(username).nickname(nickname).email(email).build();
        userMapper.insert(user);


        String name = "千里之外";
        String info = "千里之外是个啥";
        GroupDO group = GroupDO.builder().name(name).info(info).build();
        groupMapper.insert(group);

        UpdateUserInfoDTO dto = new UpdateUserInfoDTO();
        dto.setGroupIds(Arrays.asList(group.getId()));

        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
        String content = mapper.writeValueAsString(dto);

        mvc.perform(put("/cms/admin/" + user.getId())
                .contentType(MediaType.APPLICATION_JSON).content(content))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("更新用户成功"));
    }

    @Test
    public void getGroups() throws Exception {
        String name = "千里之外";
        String info = "千里之外是个啥";
        GroupDO group = GroupDO.builder().name(name).info(info).build();
        groupMapper.insert(group);

        String module = "信息";
        String permissionName = "查看lin的信息";

        PermissionDO permission = PermissionDO.builder().name(permissionName).module(module).build();
        permissionMapper.insert(permission);

        groupPermissionMapper.insert(new GroupPermissionDO(group.getId(), permission.getId()));

        mvc.perform(get("/cms/admin/groups")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.total").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.items").isArray());
    }

    @Test
    public void getAllGroup() throws Exception {
        String name = "千里之外";
        String info = "千里之外是个啥";
        GroupDO group = GroupDO.builder().name(name).info(info).build();
        groupMapper.insert(group);

        String module = "信息";
        String permissionName = "查看lin的信息";

        PermissionDO permission = PermissionDO.builder().name(permissionName).module(module).build();
        permissionMapper.insert(permission);

        groupPermissionMapper.insert(new GroupPermissionDO(group.getId(), permission.getId()));

        mvc.perform(get("/cms/admin/group/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$").isArray());
    }

    @Test
    public void getGroup() throws Exception {
        String name = "千里之外";
        String info = "千里之外是个啥";
        GroupDO group = GroupDO.builder().name(name).info(info).build();
        groupMapper.insert(group);

        String module = "信息";
        String permissionName = "查看lin的信息";

        PermissionDO permission = PermissionDO.builder().name(permissionName).module(module).build();
        permissionMapper.insert(permission);

        groupPermissionMapper.insert(new GroupPermissionDO(group.getId(), permission.getId()));

        mvc.perform(get("/cms/admin/group/" + group.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(name));
    }

    @Test
    public void createGroup() throws Exception {
        // TODO
        NewGroupDTO validator = new NewGroupDTO();

        validator.setName("flink");
        validator.setInfo("flink is a finger");
        List<String> auths = new ArrayList<>();
        auths.add("查询自己信息");
        auths.add("查询所有日志");
        auths.add("搜索日志");
        auths.add("查询日志记录的用户");
        // validator.setAuths(auths);

        mvc.perform(post("/cms/admin/group/")
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONBytes(validator)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.msg").value("新建分组成功！")
                );
    }

    @Test
    public void updateGroup() throws Exception {
        GroupDO groupDO = new GroupDO();
        groupDO.setName(name);
        groupDO.setInfo(info);
        // groupMapper.insert(groupDO);

        UpdateGroupDTO validator = new UpdateGroupDTO();
        validator.setName("storm");
        validator.setInfo("flink is a finger");

        mvc.perform(put("/cms/admin/group/" + groupDO.getId())
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONBytes(validator)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.msg").value("更新分组成功!")
                );

    }

    @Test
    public void deleteGroup() throws Exception {
        GroupDO groupDO = new GroupDO();
        groupDO.setName(name);
        groupDO.setInfo(info);
        // groupMapper.insert(groupDO);

        mvc.perform(delete("/cms/admin/group/" + groupDO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.msg").value("删除分组成功!")
                );
    }

    @Test
    public void dispatchAuth() throws Exception {
        GroupDO groupDO = new GroupDO();
        groupDO.setName(name);
        groupDO.setInfo(info);
        // groupMapper.insert(groupDO);

        this.groupId = groupDO.getId();
        AuthDO authDO = new AuthDO();
        authDO.setGroupId(groupId);
        authDO.setModule(module);
        authDO.setAuth(auth);
        authMapper.insert(authDO);

        DispatchPermissionDTO dto = new DispatchPermissionDTO();
        dto.setGroupId(groupId);
        // dto.setAuth("查询日志记录的用户");

        // 查询日志记录的用户
        mvc.perform(post("/cms/admin/dispatch")
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONBytes(dto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.msg").value("添加权限成功!")
                );
    }

    @Test
    public void dispatchAuths() throws Exception {
        GroupDO groupDO = new GroupDO();
        groupDO.setName(name);
        groupDO.setInfo(info);
        // groupMapper.insert(groupDO);

        this.groupId = groupDO.getId();
        AuthDO authDO = new AuthDO();
        authDO.setGroupId(groupId);
        authDO.setModule(module);
        authDO.setAuth(auth);
        authMapper.insert(authDO);

        DispatchPermissionsDTO dto = new DispatchPermissionsDTO();
        dto.setGroupId(groupId);
        List<String> auths = new ArrayList<>();
        auths.add("查询日志记录的用户");
        // dto.setAuths(auths);

        // 查询日志记录的用户
        mvc.perform(post("/cms/admin/dispatch/patch")
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONBytes(dto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.msg").value("添加权限成功!")
                );
    }

    @Test
    public void removeAuths() throws Exception {
        GroupDO groupDO = new GroupDO();
        groupDO.setName(name);
        groupDO.setInfo(info);
        // groupMapper.insert(groupDO);

        this.groupId = groupDO.getId();
        AuthDO authDO = new AuthDO();
        authDO.setGroupId(groupId);
        authDO.setModule(module);
        authDO.setAuth(auth);
        authMapper.insert(authDO);

        RemovePermissionsDTO dto = new RemovePermissionsDTO();
        dto.setGroupId(groupId);
        List<String> auths = new ArrayList<>();
        auths.add("查看lin的信息");
        // dto.setAuths(auths);

        // 查询日志记录的用户
        mvc.perform(post("/cms/admin/remove")
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONBytes(dto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.msg").value("删除权限成功!")
                );
    }
}