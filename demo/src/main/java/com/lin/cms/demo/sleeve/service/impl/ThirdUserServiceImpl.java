package com.lin.cms.demo.sleeve.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.common.mybatis.Page;
import com.lin.cms.demo.sleeve.mapper.ThirdUserMapper;
import com.lin.cms.demo.sleeve.model.ThirdUser;
import com.lin.cms.demo.sleeve.service.IThirdUserService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author pedro
 * @since 2019-07-23
 */
@Service
public class ThirdUserServiceImpl extends ServiceImpl<ThirdUserMapper, ThirdUser> implements IThirdUserService {

    @Override
    public PageResult<ThirdUser> getUserByPage(Integer count, Integer page) {
        Page pager = new Page(page, count);
        IPage<ThirdUser> iPage = this.getBaseMapper().selectPage(pager, null);
        List<ThirdUser> categories = iPage.getRecords();
        return PageResult.genPageResult(iPage.getTotal(), categories);
    }
}
