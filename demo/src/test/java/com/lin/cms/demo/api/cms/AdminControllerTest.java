package com.lin.cms.demo.api.cms;

import com.alibaba.fastjson.JSON;
import com.lin.cms.demo.dto.admin.*;
import com.lin.cms.demo.mapper.AuthMapper;
import com.lin.cms.demo.mapper.GroupMapper;
import com.lin.cms.demo.mapper.UserMapper;
import com.lin.cms.demo.model.AuthDO;
import com.lin.cms.demo.model.GroupDO;
import com.lin.cms.demo.model.UserDO;
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
@Transactional // 数据操作后回滚
@Rollback
@AutoConfigureMockMvc
public class AdminControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GroupMapper groupMapper;

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
    public void getAuthority() throws Exception {
        mvc.perform(get("/cms/admin/authority")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.用户").isNotEmpty()
                );
    }

    @Test
    public void getAdminUsers() throws Exception {
        mvc.perform(get("/cms/admin/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.total_nums").isNumber()
                );
    }

    @Test
    public void changeUserPassword() throws Exception {
        UserDO userDO = new UserDO();
        userDO.setNickname(nickname);
        userDO.setPasswordEncrypt(password);
        userDO.setEmail(email);
        userMapper.insert(userDO);

        String newPassword = "111111111";

        ResetPasswordDTO dto = new ResetPasswordDTO();
        dto.setNewPassword(newPassword);
        dto.setConfirmPassword(newPassword);

        mvc.perform(put("/cms/admin/password/" + userDO.getId())
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONBytes(dto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.msg").value("密码修改成功")
                );
    }

    @Test
    public void deleteUser() throws Exception {
        UserDO userDO = new UserDO();
        userDO.setNickname(nickname);
        userDO.setPasswordEncrypt(password);
        userDO.setEmail(email);
        userMapper.insert(userDO);

        mvc.perform(delete("/cms/admin/" + userDO.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.msg").value("删除用户成功")
                );
    }

    @Test
    public void updateUser() throws Exception {
        UserDO userDO = new UserDO();
        userDO.setNickname(nickname);
        userDO.setPasswordEncrypt(password);
        userDO.setEmail(email);
        userMapper.insert(userDO);

        String newEmail = "111111111@qq.com";

        UpdateUserInfoDTO dto = new UpdateUserInfoDTO();
        dto.setEmail(newEmail);
        dto.setGroupId(100L);

        mvc.perform(put("/cms/admin/" + userDO.getId())
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONBytes(dto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.msg").value("更新用户成功")
                );
    }

    @Test
    public void getAdminGroups() throws Exception {
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

        mvc.perform(get("/cms/admin/groups")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.total_nums").isNotEmpty()
                );
    }

    @Test
    public void getAllGroup() throws Exception {
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

        mvc.perform(get("/cms/admin/group/all")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$").isArray()
                );
    }

    @Test
    public void getGroup() throws Exception {
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

        mvc.perform(get("/cms/admin/group/" + this.groupId)
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.name").value(name)
                );
    }

    @Test
    public void createGroup() throws Exception {
        NewGroupDTO validator = new NewGroupDTO();

        validator.setName("flink");
        validator.setInfo("flink is a finger");
        List<String> auths = new ArrayList<>();
        auths.add("查询自己信息");
        auths.add("查询所有日志");
        auths.add("搜索日志");
        auths.add("查询日志记录的用户");
        validator.setAuths(auths);

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
        groupMapper.insert(groupDO);

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
        groupMapper.insert(groupDO);

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
        groupMapper.insert(groupDO);

        this.groupId = groupDO.getId();
        AuthDO authDO = new AuthDO();
        authDO.setGroupId(groupId);
        authDO.setModule(module);
        authDO.setAuth(auth);
        authMapper.insert(authDO);

        DispatchAuthDTO dto = new DispatchAuthDTO();
        dto.setGroupId(groupId);
        dto.setAuth("查询日志记录的用户");

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
        groupMapper.insert(groupDO);

        this.groupId = groupDO.getId();
        AuthDO authDO = new AuthDO();
        authDO.setGroupId(groupId);
        authDO.setModule(module);
        authDO.setAuth(auth);
        authMapper.insert(authDO);

        DispatchAuthsDTO dto = new DispatchAuthsDTO();
        dto.setGroupId(groupId);
        List<String> auths = new ArrayList<>();
        auths.add("查询日志记录的用户");
        dto.setAuths(auths);

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
        groupMapper.insert(groupDO);

        this.groupId = groupDO.getId();
        AuthDO authDO = new AuthDO();
        authDO.setGroupId(groupId);
        authDO.setModule(module);
        authDO.setAuth(auth);
        authMapper.insert(authDO);

        RemoveAuthsDTO dto = new RemoveAuthsDTO();
        dto.setGroupId(groupId);
        List<String> auths = new ArrayList<>();
        auths.add("查看lin的信息");
        dto.setAuths(auths);

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