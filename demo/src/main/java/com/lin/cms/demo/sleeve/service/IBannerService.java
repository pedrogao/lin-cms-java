package com.lin.cms.demo.sleeve.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.sleeve.bo.BannerAndItems;
import com.lin.cms.demo.sleeve.dto.BannerCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.model.Banner;

/**
 * @author pedro
 * @since 2019-07-23
 */
public interface IBannerService extends IService<Banner> {

    void createBanner(BannerCreateOrUpdateDTO dto);

    void updateBanner(BannerCreateOrUpdateDTO dto, Long id);

    void deleteBanner(Long id);

    PageResult<Banner> getBannerByPage(Long count, Long page);

    BannerAndItems getBannerAndItemsById(Long id);
}
