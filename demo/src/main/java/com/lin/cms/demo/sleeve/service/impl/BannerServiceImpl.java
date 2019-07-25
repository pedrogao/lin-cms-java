package com.lin.cms.demo.sleeve.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.common.mybatis.Page;
import com.lin.cms.demo.sleeve.dto.BannerCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.mapper.BannerMapper;
import com.lin.cms.demo.sleeve.model.Banner;
import com.lin.cms.demo.sleeve.service.IBannerService;
import com.lin.cms.exception.NotFound;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pedro
 * @since 2019-07-23
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements IBannerService {

    @Override
    public void createBanner(BannerCreateOrUpdateDTO dto) {
        Banner banner = new Banner();
        BeanUtils.copyProperties(dto, banner);
        this.save(banner);
    }

    @Override
    public void updateBanner(BannerCreateOrUpdateDTO dto, Long id) {
        Banner exist = this.getById(id);
        if (exist == null) {
            throw new NotFound("未找到相关的banner");
        }
        BeanUtils.copyProperties(dto, exist);
        this.updateById(exist);
    }

    @Override
    public void deleteBanner(Long id) {
        Banner exist = this.getById(id);
        if (exist == null) {
            throw new NotFound("未找到相关的banner");
        }
        this.getBaseMapper().deleteById(id);
    }

    @Override
    public PageResult<Banner> getBannerByPage(Long count, Long page) {
        Page pager = new Page(page, count);
        IPage<Banner> iPage = this.getBaseMapper().selectPage(pager, null);
        List<Banner> categories = iPage.getRecords();
        return PageResult.genPageResult(iPage.getTotal(), categories);
    }
}
