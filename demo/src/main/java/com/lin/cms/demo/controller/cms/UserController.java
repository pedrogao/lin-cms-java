package com.lin.cms.demo.controller.cms;

import com.lin.cms.core.annotation.AdminRequired;
import com.lin.cms.core.annotation.LoginRequired;
import com.lin.cms.core.annotation.RefreshRequired;
import com.lin.cms.core.annotation.RouteMeta;
import com.lin.cms.demo.common.LocalUser;
import com.lin.cms.demo.v2.service.UserIdentityService;
import com.lin.cms.exception.HttpException;
import com.lin.cms.demo.vo.CommonResult;
import com.lin.cms.demo.v2.model.UserDO;
import com.lin.cms.demo.vo.UserPermissionsVO;
import com.lin.cms.demo.common.utils.ResultUtil;
import com.lin.cms.demo.v2.service.UserService;
import com.lin.cms.token.DoubleJWT;
import com.lin.cms.demo.dto.user.*;
import com.lin.cms.token.Tokens;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Created by lin on 2019/05/23.
 * License MIT
 */

@RestController
@RequestMapping("/cms/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserIdentityService userIdentityService;

    @Autowired
    private DoubleJWT jwt;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    @AdminRequired
    public CommonResult<String> register(@RequestBody @Validated RegisterDTO validator) {
        userService.createUser(validator);
        return ResultUtil.generateSuccessResult("添加用户成功！");
    }

    /**
     * 用户登陆
     */
    @PostMapping("/login")
    public Tokens login(@RequestBody @Validated LoginDTO validator) {
        UserDO user = userService.getUserByUsername(validator.getUsername());
        if (user == null) {
            throw new HttpException("user not found");
        }
        boolean valid = userIdentityService.verifyUsernamePassword(
                user.getId(),
                user.getUsername(),
                validator.getPassword());
        if (!valid)
            throw new HttpException("username or password is fault");
        return jwt.generateTokens(user.getId());
    }

    /**
     * 更新用户信息
     */
    @PutMapping
    @LoginRequired
    public CommonResult update(@RequestBody @Validated UpdateInfoDTO validator) {
        userService.updateUserInfo(validator);
        return ResultUtil.generateSuccessResult("更新成功！");
    }

    /**
     * 修改密码
     */
    @PutMapping("/change_password")
    @LoginRequired
    public CommonResult updatePassword(@RequestBody @Validated ChangePasswordDTO validator) {
        userService.changeUserPassword(validator);
        return ResultUtil.generateSuccessResult("密码修改成功！");
    }

    /**
     * 刷新令牌
     */
    @GetMapping("/refresh")
    @RefreshRequired
    public Tokens refreshToken() {
        UserDO user = LocalUser.getLocalUser();
        return jwt.generateTokens(user.getId());
    }

    /**
     * 查询拥有权限
     */
    @GetMapping("/permissions")
    @LoginRequired
    @RouteMeta(permission = "查询自己拥有的权限", module = "用户", mount = true)
    public UserPermissionsVO getAuths() {
        UserDO user = LocalUser.getLocalUser();
        // if (user.checkAdmin()) {
        //     return new UserPermissionsVO(user);
        // }
        List<Map<String, List<Map<String, String>>>> permissions = userService.getStructualUserPermissions(user.getId());
        return new UserPermissionsVO(user, permissions);
    }

    /**
     * 查询自己信息
     */
    @LoginRequired
    @RouteMeta(permission = "查询自己信息", module = "用户", mount = true)
    @GetMapping("/information")
    public UserDO getInformation() {
        UserDO user = LocalUser.getLocalUser();
        return user;
    }

    /**
     * 修改头像
     */
    // @LoginRequired
    // @PutMapping("/avatar")
    // public CommonResult updateAvatar(@RequestBody @Validated AvatarUpdateDTO validator) {
    //     return ResultUtil.generateSuccessResult("头像更新成功！");
    // }
}
