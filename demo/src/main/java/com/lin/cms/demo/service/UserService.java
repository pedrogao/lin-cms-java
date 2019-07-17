package com.lin.cms.demo.service;

import com.lin.cms.demo.model.UserDO;
import com.lin.cms.exception.Forbidden;
import com.lin.cms.exception.Parameter;
import com.lin.cms.demo.dto.user.RegisterDTO;
import com.lin.cms.demo.dto.user.UpdateInfoDTO;
import com.lin.cms.demo.dto.user.AvatarUpdateDTO;
import com.lin.cms.demo.dto.user.ChangePasswordDTO;

import java.util.List;
import java.util.Map;


/**
 * Created by lin on 2019/06/06.
 */
public interface UserService {

    void createUser(RegisterDTO validator) throws Forbidden;

    void updateUser(UpdateInfoDTO validator) throws Parameter;

    void changePassword(ChangePasswordDTO validator) throws Parameter;

    void updateAvatar(AvatarUpdateDTO validator);

    List<Map<String, List<Map<String, String>>>> getAuths(Integer groupId);

    UserDO findByNickname(String nickname);
}
