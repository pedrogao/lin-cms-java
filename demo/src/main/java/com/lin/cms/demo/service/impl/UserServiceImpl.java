package com.lin.cms.demo.service.impl;

import com.lin.cms.demo.db.AbstractService;
import com.lin.cms.core.exception.NotFound;
import com.lin.cms.core.exception.Parameter;
import com.lin.cms.demo.mapper.UserMapper;
import com.lin.cms.demo.model.User;
import com.lin.cms.demo.service.UserService;
import com.lin.cms.demo.validators.user.AvatarUpdateValidator;
import com.lin.cms.demo.validators.user.ChangePasswordValidator;
import com.lin.cms.demo.validators.user.RegisterValidator;
import com.lin.cms.demo.validators.user.UpdateInfoValidator;
import com.lin.cms.utils.LocalUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by lin on 2019/06/06.
 */
@Service
public class UserServiceImpl extends AbstractService<User> implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public void createUser(RegisterValidator validator) throws NotFound {

        User exist = this.findBy("nickname", validator.getNickname());
        if (exist != null) {
            throw new NotFound("用户已经存在");
        }

        User user = new User();
        user.setNickname(validator.getNickname());
        user.setPasswordEncrypt(validator.getPassword());
        user.setGroupId(validator.getGroupId());
        user.setNickname(validator.getNickname());
        if (validator.getEmail() != null) {
            user.setEmail(validator.getEmail());
        }
        this.saveWithTimeCreate(user);
    }

    @Override
    public void updateUser(UpdateInfoValidator validator) throws Parameter {
        String email = validator.getEmail();
        User user = LocalUser.getLocalUser(User.class);
        if (!user.getEmail().equals(email)) {
            User exist = this.findBy("email", validator.getEmail());
            if (exist != null) {
                throw new Parameter("邮箱已被注册，请重新输入邮箱");
            }
        }
        user.setEmail(email);
        this.update(user);
    }

    @Override
    public void changePassword(ChangePasswordValidator validator) throws Parameter {
        User user = LocalUser.getLocalUser(User.class);
        boolean valid = user.verify(validator.getOldPassword());
        if (!valid) {
            throw new Parameter("请输入正确的旧密码");
        }
        user.setPasswordEncrypt(validator.getNewPassword());
        this.update(user);
    }

    @Override
    public void updateAvatar(AvatarUpdateValidator validator) {
        User user = LocalUser.getLocalUser(User.class);
        user.setAvatar(validator.getAvatar());
        this.update(user);
    }
}
