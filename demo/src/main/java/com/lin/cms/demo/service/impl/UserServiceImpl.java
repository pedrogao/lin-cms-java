package com.lin.cms.demo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lin.cms.demo.mapper.GroupMapper;
import com.lin.cms.demo.model.GroupDO;
import com.lin.cms.demo.mapper.AuthMapper;
import com.lin.cms.demo.mapper.UserMapper;
import com.lin.cms.demo.model.SimpleAuthDO;
import com.lin.cms.demo.model.UserDO;
import com.lin.cms.demo.service.UserService;
import com.lin.cms.demo.common.AuthSpliter;
import com.lin.cms.demo.common.LocalUserLegacy;
import com.lin.cms.demo.dto.user.RegisterDTO;
import com.lin.cms.demo.dto.user.UpdateInfoDTO;
import com.lin.cms.demo.dto.user.AvatarUpdateDTO;
import com.lin.cms.demo.dto.user.ChangePasswordDTO;
import com.lin.cms.exception.ParameterException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


/**
 * Created by lin on 2019/06/06.
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthMapper authMapper;

    @Autowired
    private GroupMapper groupMapper;


    @Override
    public void createUser(RegisterDTO validator) {
        // TODO
        // UserDO exist = this.findByNickname(validator.getNickname());
        // if (exist != null) {
        //     throw new ForbiddenException("用户已经存在");
        // }
        UserDO user = new UserDO();
        // user.setNickname(validator.getNickname());
        // user.setPasswordEncrypt(validator.getPassword());
        // user.setGroupId(validator.getGroupId());
        // user.setNickname(validator.getNickname());
        // if (validator.getEmail() != null) {
        //     user.setEmail(validator.getEmail());
        // }
        userMapper.insert(user);
    }

    @Override
    public void updateUser(UpdateInfoDTO validator) {
        String email = validator.getEmail();
        UserDO user = LocalUserLegacy.getLocalUser();
        if (!user.getEmail().equals(email)) {
            QueryWrapper<UserDO> wrapper = new QueryWrapper<>();
            wrapper.eq("email", validator.getEmail());
            UserDO exist = userMapper.selectOne(wrapper);
            if (exist != null) {
                throw new ParameterException("邮箱已被注册，请重新输入邮箱");
            }
        }
        user.setEmail(email);
        userMapper.updateById(user);
    }

    @Override
    public void changePassword(ChangePasswordDTO validator) {
        UserDO user = LocalUserLegacy.getLocalUser();
        boolean valid = user.verify(validator.getOldPassword());
        if (!valid) {
            throw new ParameterException("请输入正确的旧密码");
        }
        user.setPasswordEncrypt(validator.getNewPassword());
        userMapper.updateById(user);
    }

    @Override
    public void updateAvatar(AvatarUpdateDTO validator) {
        UserDO user = LocalUserLegacy.getLocalUser();
        user.setAvatar(validator.getAvatar());
        userMapper.updateById(user);
    }

    @Override
    public List<Map<String, List<Map<String, String>>>> getAuths(Long groupId) {
        List<SimpleAuthDO> auths = authMapper.findByGroupId(groupId);
        return AuthSpliter.splitAuths(auths);
    }

    @Override
    public UserDO findByNickname(String nickname) {
        QueryWrapper<UserDO> wrapper = new QueryWrapper<>();
        wrapper.eq("nickname", nickname);
        UserDO exist = userMapper.selectOne(wrapper);
        return exist;
    }

    @Override
    public GroupDO findGroupByUserId(Long groupId) {
        return groupMapper.selectById(groupId);
    }
}
