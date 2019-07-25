package com.lin.cms.demo.api.cms;

import com.alibaba.fastjson.JSON;
import com.lin.cms.demo.dto.user.*;
import com.lin.cms.demo.mapper.UserMapper;
import com.lin.cms.demo.model.UserDO;
import com.lin.cms.demo.utils.LocalUser;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional // 数据操作后回滚
@Rollback
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserMapper userMapper;

    private String email = "13129982604@qq.com";
    private String password = "123456";
    private Long groupId = 100L;
    private String nickname = "pedro";

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void register() throws Exception {
        RegisterDTO dto = new RegisterDTO();
        dto.setGroupId(groupId);
        dto.setEmail(email);
        dto.setConfirmPassword(password);
        dto.setPassword(password);
        dto.setNickname(nickname);

        mvc.perform(post("/cms/user/register")
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONBytes(dto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.msg").value("添加用户成功！"));
    }

    @Test
    public void login() throws Exception {
        UserDO userDO = new UserDO();
        userDO.setNickname(nickname);
        userDO.setPasswordEncrypt(password);
        userDO.setEmail(email);

        userMapper.insert(userDO);

        LoginDTO dto = new LoginDTO();
        dto.setNickname(nickname);
        dto.setPassword(password);

        mvc.perform(post("/cms/user/login")
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONBytes(dto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.access_token").isNotEmpty());
    }

    @Test
    public void update() throws Exception {
        UserDO userDO = new UserDO();
        userDO.setNickname(nickname);
        userDO.setPasswordEncrypt(password);
        userDO.setEmail(email);

        userMapper.insert(userDO);

        UpdateInfoDTO dto = new UpdateInfoDTO();
        dto.setEmail("23129982604@qq.com");

        LocalUser.setLocalUser(userDO);

        mvc.perform(MockMvcRequestBuilders.put("/cms/user/")
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONBytes(dto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.msg").value("更新成功！"));
    }

    @Test
    public void updatePassword() throws Exception {
        UserDO userDO = new UserDO();
        userDO.setNickname(nickname);
        userDO.setPasswordEncrypt(password);
        userDO.setEmail(email);

        userMapper.insert(userDO);

        ChangePasswordDTO dto = new ChangePasswordDTO();
        dto.setOldPassword(password);
        dto.setNewPassword("147258");
        dto.setConfirmPassword("147258");

        LocalUser.setLocalUser(userDO);

        mvc.perform(MockMvcRequestBuilders.put("/cms/user/change_password")
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONBytes(dto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.msg").value("密码修改成功！"));
    }

    @Test
    public void refreshToken() throws Exception {
        UserDO userDO = new UserDO();
        userDO.setNickname(nickname);
        userDO.setPasswordEncrypt(password);
        userDO.setEmail(email);

        userMapper.insert(userDO);
        LocalUser.setLocalUser(userDO);

        mvc.perform(MockMvcRequestBuilders.get("/cms/user/refresh")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.access_token").isNotEmpty());
    }

    @Test
    public void getAuths() {
        // TODO
    }

    @Test
    public void getInformation() throws Exception {
        UserDO userDO = new UserDO();
        userDO.setNickname(nickname);
        userDO.setPasswordEncrypt(password);
        userDO.setEmail(email);

        userMapper.insert(userDO);
        LocalUser.setLocalUser(userDO);

        mvc.perform(MockMvcRequestBuilders.get("/cms/user/information")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.nickname").value(nickname));
    }

    @Test
    public void updateAvatar() throws Exception {
        UserDO userDO = new UserDO();
        userDO.setNickname(nickname);
        userDO.setPasswordEncrypt(password);
        userDO.setEmail(email);

        userMapper.insert(userDO);
        LocalUser.setLocalUser(userDO);

        AvatarUpdateDTO dto = new AvatarUpdateDTO();
        dto.setAvatar("ijbiigguiiubbjibi.png");

        mvc.perform(MockMvcRequestBuilders.put("/cms/user/avatar")
                .contentType(MediaType.APPLICATION_JSON).content(JSON.toJSONBytes(dto)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.
                        jsonPath("$.msg").value("头像更新成功！"));
    }
}