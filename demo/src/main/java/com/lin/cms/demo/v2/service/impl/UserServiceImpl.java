package com.lin.cms.demo.v2.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lin.cms.demo.dto.user.ChangePasswordDTO;
import com.lin.cms.demo.dto.user.RegisterDTO;
import com.lin.cms.demo.dto.user.UpdateInfoDTO;
import com.lin.cms.demo.v2.mapper.UserMapper;
import com.lin.cms.demo.v2.model.UserDO;
import com.lin.cms.demo.v2.service.UserIdentityService;
import com.lin.cms.demo.v2.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.cms.exception.Forbidden;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户业务实现类
 * </p>
 *
 * @author pedro
 * @since 2019-11-30
 */
@Service("userServiceImpl-v2")
public class UserServiceImpl extends ServiceImpl<UserMapper, UserDO> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserIdentityService userIdentityService;

    @Transactional
    @Override
    public UserDO createUser(RegisterDTO dto) {
        boolean exist = this.checkUserExistByUsername(dto.getUsername());
        if (exist) {
            throw new Forbidden("已经有用户使用了该名称，请重新输入新的用户名");
        }
        UserDO user = new UserDO();
        user.setUsername(dto.getUsername());
        if (dto.getEmail() != null) {
            user.setEmail(dto.getEmail());
        }
        userMapper.insert(user);
        userIdentityService.createUsernamePasswordIdentity(user.getId(), dto.getUsername(), (dto.getPassword()));
        return user;
    }

    @Override
    public UserDO updateUserInfo(UpdateInfoDTO dto) {
        return null;
    }

    @Override
    public UserDO changeUserPassword(ChangePasswordDTO dto) {
        return null;
    }

    @Override
    public List<Map<String, List<Map<String, String>>>> getUserPermissions(Long userId) {
        return null;
    }

    @Override
    public UserDO findByUsername(String username) {
        QueryWrapper<UserDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserDO::getUsername, username);
        UserDO user = this.getOne(wrapper);
        return user;
    }

    @Override
    public boolean checkUserExistByUsername(String username) {
        int rows = userMapper.selectCountByUsername(username);
        return rows > 0;
    }

    @Override
    public boolean checkUserExistById(Long id) {
        int rows = userMapper.selectCountById(id);
        return rows > 0;
    }
}
