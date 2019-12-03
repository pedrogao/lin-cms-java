package com.lin.cms.demo.v2.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lin.cms.demo.common.consts.IdentityConsts;
import com.lin.cms.demo.common.utils.EncryptUtil;
import com.lin.cms.demo.v2.model.UserIdentityDO;
import com.lin.cms.demo.v2.mapper.UserIdentityMapper;
import com.lin.cms.demo.v2.service.UserIdentityService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pedro
 * @since 2019-12-02
 */
@Service
public class UserIdentityServiceImpl extends ServiceImpl<UserIdentityMapper, UserIdentityDO> implements UserIdentityService {

    @Autowired
    private UserIdentityMapper userIdentityMapper;

    @Override
    public UserIdentityDO createIdentity(Long userId, String identityType, String identifier, String credential) {
        UserIdentityDO userIdentity = new UserIdentityDO();
        userIdentity.setUserId(userId);
        userIdentity.setIdentityType(identityType);
        userIdentity.setIdentifier(identifier);
        userIdentity.setCredential(credential);
        return this.createIdentity(userIdentity);
    }

    @Override
    public UserIdentityDO createIdentity(UserIdentityDO userIdentity) {
        userIdentityMapper.insert(userIdentity);
        return userIdentity;
    }

    @Override
    public UserIdentityDO createUsernamePasswordIdentity(Long userId, String identifier, String credential) {
        // 密码加密
        credential = EncryptUtil.encrypt(credential);
        return this.createIdentity(userId, IdentityConsts.USERNAME_PASSWORD_IDENTITY, identifier, credential);
    }

    @Override
    public boolean verifyUsernamePassword(Long userId, String username, String password) {
        QueryWrapper<UserIdentityDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserIdentityDO::getUserId, userId)
                .eq(UserIdentityDO::getIdentityType, IdentityConsts.USERNAME_PASSWORD_IDENTITY)
                .eq(UserIdentityDO::getIdentifier, username);
        UserIdentityDO userIdentity = userIdentityMapper.selectOne(wrapper);
        return EncryptUtil.verify(userIdentity.getCredential(), password);
    }

    @Override
    public boolean changePassword(Long userId, String password) {
        String encrypted = EncryptUtil.encrypt(password);
        UserIdentityDO userIdentity = new UserIdentityDO();
        userIdentity.setCredential(encrypted);
        QueryWrapper<UserIdentityDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserIdentityDO::getUserId, userId);
        return userIdentityMapper.update(userIdentity, wrapper) > 0;
    }

    @Override
    public boolean changeUsername(Long userId, String username) {
        UserIdentityDO userIdentity = new UserIdentityDO();
        userIdentity.setIdentifier(username);
        QueryWrapper<UserIdentityDO> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(UserIdentityDO::getUserId, userId);
        return userIdentityMapper.update(userIdentity, wrapper) > 0;
    }
}
