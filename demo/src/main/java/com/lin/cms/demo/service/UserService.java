package com.lin.cms.demo.service;

import com.lin.cms.core.exception.NotFound;
import com.lin.cms.core.exception.Parameter;
import com.lin.cms.demo.model.UserDO;
import com.lin.cms.demo.dto.user.RegisterDTO;
import com.lin.cms.demo.dto.user.UpdateInfoDTO;
import com.lin.cms.demo.service.base.Service;
import com.lin.cms.demo.dto.user.AvatarUpdateDTO;
import com.lin.cms.demo.dto.user.ChangePasswordDTO;


/**
 * Created by lin on 2019/06/06.
 */
public interface UserService extends Service<UserDO> {

    void createUser(RegisterDTO validator) throws NotFound;

    void updateUser(UpdateInfoDTO validator) throws Parameter;

    void changePassword(ChangePasswordDTO validator) throws Parameter;

    void updateAvatar(AvatarUpdateDTO validator);

    // void getAuths()

}
