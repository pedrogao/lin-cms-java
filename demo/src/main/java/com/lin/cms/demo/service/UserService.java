package com.lin.cms.demo.service;

import com.lin.cms.core.exception.NotFound;
import com.lin.cms.core.exception.Parameter;
import com.lin.cms.demo.model.UserDO;
import com.lin.cms.demo.validators.user.RegisterValidator;
import com.lin.cms.demo.validators.user.UpdateInfoValidator;
import com.lin.cms.demo.service.base.Service;
import com.lin.cms.demo.validators.user.AvatarUpdateValidator;
import com.lin.cms.demo.validators.user.ChangePasswordValidator;


/**
 * Created by lin on 2019/06/06.
 */
public interface UserService extends Service<UserDO> {

    void createUser(RegisterValidator validator) throws NotFound;

    void updateUser(UpdateInfoValidator validator) throws Parameter;

    void changePassword(ChangePasswordValidator validator) throws Parameter;

    void updateAvatar(AvatarUpdateValidator validator);

    // void getAuths()

}
