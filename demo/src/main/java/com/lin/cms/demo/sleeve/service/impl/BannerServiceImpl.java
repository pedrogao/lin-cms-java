package com.lin.cms.demo.sleeve.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.common.mybatis.Page;
import com.lin.cms.demo.sleeve.bo.BannerAndItems;
import com.lin.cms.demo.sleeve.dto.BannerCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.mapper.BannerItemMapper;
import com.lin.cms.demo.sleeve.mapper.BannerMapper;
import com.lin.cms.demo.sleeve.model.Banner;
import com.lin.cms.demo.sleeve.model.BannerItem;
import com.lin.cms.demo.sleeve.service.IBannerService;
import com.lin.cms.exception.Forbidden;
import com.lin.cms.exception.NotFound;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pedro
 * @since 2019-07-23
 */
@Service
public class BannerServiceImpl extends ServiceImpl<BannerMapper, Banner> implements IBannerService {


    @Autowired
    private BannerItemMapper bannerItemMapper;

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
        QueryWrapper<BannerItem> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(BannerItem::getBannerId, id);
        wrapper.last("LIMIT 1");
        BannerItem item = bannerItemMapper.selectOne(wrapper);
        if (item != null) {
            throw new Forbidden("Banner下存在Item项，不可删除！");
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

    @Override
    public BannerAndItems getBannerAndItemsById(Long id) {
        Banner exist = this.getById(id);
        if (exist == null) {
            throw new NotFound("未找到相关的banner");
        }
        QueryWrapper<BannerItem> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(BannerItem::getBannerId, id);
        List<BannerItem> items = bannerItemMapper.selectList(wrapper);
        BannerAndItems bannerAndItems = new BannerAndItems(exist, items);
        return bannerAndItems;
    }
}
