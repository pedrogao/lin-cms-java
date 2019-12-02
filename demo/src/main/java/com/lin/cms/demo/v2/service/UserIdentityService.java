package com.lin.cms.demo.v2.service;

import com.lin.cms.demo.v2.model.UserIdentityDO;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.validation.constraints.NotNull;

/**
 * @author pedro
 * @since 2019-12-02
 */
public interface UserIdentityService extends IService<UserIdentityDO> {

    /**
     * 新建用户认证信息
     *
     * @param userId       用户id
     * @param identityType 认证类型
     * @param identifier   标识
     * @param credential   凭证
     * @return 用户认证
     */
    UserIdentityDO createIdentity(Long userId,
                                  String identityType,
                                  String identifier,
                                  String credential);

    /**
     * 新建用户认证信息
     *
     * @param userIdentity 用户认证信息
     * @return 用户认证
     */
    UserIdentityDO createIdentity(UserIdentityDO userIdentity);

    /**
     * 新建用户认证信息 (USERNAME_PASSWORD)
     *
     * @param userId   用户id
     * @param username 用户名
     * @param password 密码
     * @return 用户认证
     */
    UserIdentityDO createUsernamePasswordIdentity(Long userId,
                                                  String username,
                                                  String password);


    /**
     * 验证用户认证信息 (USERNAME_PASSWORD)
     *
     * @param userId   用户id
     * @param username 用户名
     * @param password 密码
     * @return 是否验证成功
     */
    boolean verifyUsernamePassword(Long userId, String username, String password);
}
