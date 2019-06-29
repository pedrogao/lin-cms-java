package com.lin.cms.base.service;

import com.lin.cms.base.model.UserDO;
import com.lin.cms.base.validators.user.AvatarUpdateValidator;
import com.lin.cms.base.validators.user.ChangePasswordValidator;
import com.lin.cms.base.validators.user.RegisterValidator;
import com.lin.cms.base.validators.user.UpdateInfoValidator;
import com.lin.cms.core.exception.NotFound;
import com.lin.cms.core.exception.Parameter;
import com.lin.cms.base.service.base.Service;


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
