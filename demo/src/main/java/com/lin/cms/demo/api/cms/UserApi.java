package com.lin.cms.demo.api.cms;

import com.lin.cms.core.annotation.AdminRequired;
import com.lin.cms.core.annotation.LoginRequired;
import com.lin.cms.core.annotation.RefreshRequired;
import com.lin.cms.core.annotation.RouteMeta;
import com.lin.cms.core.exception.HttpException;
import com.lin.cms.core.exception.NotFound;
import com.lin.cms.core.exception.Parameter;
import com.lin.cms.core.result.Result;
import com.lin.cms.demo.model.UserPO;
import com.lin.cms.demo.utils.LocalUser;
import com.lin.cms.core.result.ResultGenerator;
import com.lin.cms.demo.service.UserService;
import com.lin.cms.token.JWT;
import com.lin.cms.demo.validators.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by lin on 2019/05/23.
 * License MIT
 */

@RestController
@RequestMapping("/cms/user")
public class UserApi {

    @Autowired
    private UserService userService;

    @Autowired
    private JWT jwt;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    @AdminRequired
    public Result<String> register(@RequestBody @Valid RegisterValidator validator) throws NotFound {
        userService.createUser(validator);
        return ResultGenerator.genSuccessResult("添加成功！");
    }

    /**
     * 用户登陆
     */
    @PostMapping("/login")
    public Map login(@RequestBody @Valid LoginValidator validator) throws HttpException {
        UserPO user = userService.findBy("nickname", validator.getNickname());
        if (user == null) {
            throw new NotFound("未找到相关用户");
        }
        Map res = jwt.generateTokens(user.getId());
        return res;
    }

    /**
     * 更新用户信息
     */
    @PutMapping
    @LoginRequired
    public Result update(@RequestBody @Valid UpdateInfoValidator validator) throws Parameter {
        userService.updateUser(validator);
        return ResultGenerator.genSuccessResult("更新成功！");
    }

    /**
     * 修改密码
     */
    @PutMapping("/change_password")
    @LoginRequired
    public Result updatePassword(@RequestBody @Valid ChangePasswordValidator validator) throws Parameter {
        userService.changePassword(validator);
        return ResultGenerator.genSuccessResult("密码修改成功！");
    }

    /**
     * 刷新令牌
     */
    @GetMapping("/refresh")
    @RefreshRequired
    public Map refreshToken() {
        UserPO user = LocalUser.getLocalUser(UserPO.class);
        Map res = jwt.generateTokens(user.getId());
        return res;
    }

    /**
     * 查询拥有权限
     */
    @GetMapping("/auths")
    @LoginRequired
    public Map getAuths() {
        // BaseUserModel user = LocalUser.getLocalUser();
        UserPO user = LocalUser.getLocalUser(UserPO.class);
        // TODO: getAuths，LocalUser, AuthInterceptor
        return new HashMap();
    }

    /**
     * 查询自己信息
     */
    @LoginRequired
    @RouteMeta(auth = "查询自己信息", module = "用户", mount = true)
    @GetMapping("/information")
    public UserPO getInformation() {
        UserPO user = LocalUser.getLocalUser(UserPO.class);
        return user;
    }

    /**
     * 修改头像
     */
    @LoginRequired
    @PutMapping("/avatar")
    public Result updateAvatar(@RequestBody @Valid AvatarUpdateValidator validator) {
        userService.updateAvatar(validator);
        return ResultGenerator.genSuccessResult("头像更新成功！");
    }
}
