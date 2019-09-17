package com.lin.cms.demo.sleeve.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.common.mybatis.Page;
import com.lin.cms.demo.sleeve.mapper.ThirdUserMapper;
import com.lin.cms.demo.sleeve.model.ThirdUser;
import com.lin.cms.demo.sleeve.model.ThirdUserParsedDO;
import com.lin.cms.demo.sleeve.service.IThirdUserService;
import com.lin.cms.exception.NotFound;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pedro
 * @since 2019-07-23
 */
@Service
public class ThirdUserServiceImpl extends ServiceImpl<ThirdUserMapper, ThirdUser> implements IThirdUserService {

    @Override
    public PageResult<ThirdUser> getUserByPage(Long count, Long page) {
        Page pager = new Page(page, count);
        IPage<ThirdUser> iPage = this.getBaseMapper().selectPage(pager, null);
        List<ThirdUser> categories = iPage.getRecords();
        return PageResult.genPageResult(iPage.getTotal(), categories, page, count);
    }

    @Override
    public ThirdUserParsedDO getParsedUserById(Long id) {
        ThirdUser user = this.getBaseMapper().selectById(id);
        if (user == null) {
            throw new NotFound("未找到相关的用户");
        }
        return new ThirdUserParsedDO(user);
    }
}
