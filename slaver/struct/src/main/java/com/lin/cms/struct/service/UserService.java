package com.lin.cms.struct.service;

import com.lin.cms.struct.model.UserDO;
import com.lin.cms.struct.service.base.Service;
import com.lin.cms.struct.validators.user.AvatarUpdateValidator;
import com.lin.cms.struct.validators.user.ChangePasswordValidator;
import com.lin.cms.struct.validators.user.RegisterValidator;
import com.lin.cms.struct.validators.user.UpdateInfoValidator;
import com.lin.cms.core.exception.NotFound;
import com.lin.cms.core.exception.Parameter;


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
