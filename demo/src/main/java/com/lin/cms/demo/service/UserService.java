package com.lin.cms.demo.service;

import com.lin.cms.demo.model.GroupDO;
import com.lin.cms.demo.model.UserDO;
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

    void createUser(RegisterDTO validator);

    void updateUser(UpdateInfoDTO validator);

    void changePassword(ChangePasswordDTO validator);

    void updateAvatar(AvatarUpdateDTO validator);

    List<Map<String, List<Map<String, String>>>> getAuths(Long groupId);

    UserDO findByNickname(String nickname);

    GroupDO findGroupByUserId(Long groupId);
}
