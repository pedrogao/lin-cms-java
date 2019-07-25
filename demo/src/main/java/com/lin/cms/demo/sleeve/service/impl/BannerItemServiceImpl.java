package com.lin.cms.demo.sleeve.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.cms.demo.sleeve.dto.BannerItemCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.mapper.BannerItemMapper;
import com.lin.cms.demo.sleeve.model.BannerItem;
import com.lin.cms.demo.sleeve.service.IBannerItemService;
import com.lin.cms.exception.NotFound;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author pedro
 * @since 2019-07-23
 */
@Service
public class BannerItemServiceImpl extends ServiceImpl<BannerItemMapper, BannerItem> implements IBannerItemService {

    @Override
    public void createBannerItem(BannerItemCreateOrUpdateDTO dto) {
        BannerItem bannerItem = new BannerItem();
        BeanUtils.copyProperties(dto, bannerItem);
        this.save(bannerItem);
    }

    @Override
    public void updateBannerItem(BannerItemCreateOrUpdateDTO dto, Integer id) {
        BannerItem exist = this.getById(id);
        if (exist == null) {
            throw new NotFound("未找到相关的banner-item");
        }
        BeanUtils.copyProperties(dto, exist);
        this.updateById(exist);
    }

    @Override
    public void deleteBannerItem(Integer id) {
        BannerItem exist = this.getById(id);
        if (exist == null) {
            throw new NotFound("未找到相关的banner-item");
        }
        this.getBaseMapper().deleteById(id);
    }
}
