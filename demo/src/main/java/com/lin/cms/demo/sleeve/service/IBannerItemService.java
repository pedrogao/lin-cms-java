package com.lin.cms.demo.sleeve.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.cms.demo.sleeve.dto.BannerItemCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.model.BannerItem;

/**
 * @author pedro
 * @since 2019-07-23
 */
public interface IBannerItemService extends IService<BannerItem> {

    void createBannerItem(BannerItemCreateOrUpdateDTO dto);

    void updateBannerItem(BannerItemCreateOrUpdateDTO dto, Long id);

    void deleteBannerItem(Long id);
}
