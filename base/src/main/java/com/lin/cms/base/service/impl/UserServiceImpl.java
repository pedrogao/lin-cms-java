package com.lin.cms.base.service.impl;

import com.lin.cms.base.mapper.UserMapper;
import com.lin.cms.base.model.UserDO;
import com.lin.cms.base.service.UserService;
import com.lin.cms.base.service.base.AbstractService;
import com.lin.cms.base.utils.LocalUser;
import com.lin.cms.base.validators.user.AvatarUpdateValidator;
import com.lin.cms.base.validators.user.ChangePasswordValidator;
import com.lin.cms.base.validators.user.RegisterValidator;
import com.lin.cms.base.validators.user.UpdateInfoValidator;
import com.lin.cms.core.exception.NotFound;
import com.lin.cms.core.exception.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by lin on 2019/06/06.
 */
@Service
public class UserServiceImpl extends AbstractService<UserDO> implements UserService {

    @Autowired
    private UserMapper userMapper;


    @Override
    public void createUser(RegisterValidator validator) throws NotFound {

        UserDO exist = this.findBy("nickname", validator.getNickname());
        if (exist != null) {
            throw new NotFound("用户已经存在");
        }

        UserDO user = new UserDO();
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
        UserDO user = LocalUser.getLocalUser(UserDO.class);
        if (!user.getEmail().equals(email)) {
            UserDO exist = this.findBy("email", validator.getEmail());
            if (exist != null) {
                throw new Parameter("邮箱已被注册，请重新输入邮箱");
            }
        }
        user.setEmail(email);
        this.update(user);
    }

    @Override
    public void changePassword(ChangePasswordValidator validator) throws Parameter {
        UserDO user = LocalUser.getLocalUser(UserDO.class);
        boolean valid = user.verify(validator.getOldPassword());
        if (!valid) {
            throw new Parameter("请输入正确的旧密码");
        }
        user.setPasswordEncrypt(validator.getNewPassword());
        this.update(user);
    }

    @Override
    public void updateAvatar(AvatarUpdateValidator validator) {
        UserDO user = LocalUser.getLocalUser(UserDO.class);
        user.setAvatar(validator.getAvatar());
        this.update(user);
    }
}
