package com.lin.cms.demo.sleeve.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.sleeve.model.ThirdUser;
import com.lin.cms.demo.sleeve.model.ThirdUserParsedDO;

/**
 * @author pedro
 * @since 2019-07-23
 */
public interface IThirdUserService extends IService<ThirdUser> {
    PageResult<ThirdUser> getUserByPage(Long count, Long page);

    ThirdUserParsedDO getParsedUserById(Long id);
}
