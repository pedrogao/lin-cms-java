package com.lin.cms.demo.v2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lin.cms.demo.dto.user.ChangePasswordDTO;
import com.lin.cms.demo.dto.user.RegisterDTO;
import com.lin.cms.demo.dto.user.UpdateInfoDTO;
import com.lin.cms.demo.v2.mapper.UserMapper;
import com.lin.cms.demo.v2.model.UserDO;
import com.lin.cms.demo.v2.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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

    @Override
    public UserDO createUser(RegisterDTO validator) {
        return null;
    }

    @Override
    public UserDO updateUserInfo(UpdateInfoDTO validator) {
        return null;
    }

    @Override
    public UserDO changeUserPassword(ChangePasswordDTO validator) {
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
}
