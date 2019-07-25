package com.lin.cms.demo.sleeve.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.sleeve.dto.BannerCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.model.Banner;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author pedro
 * @since 2019-07-23
 */
public interface IBannerService extends IService<Banner> {

    void createBanner(BannerCreateOrUpdateDTO dto);

    void updateBanner(BannerCreateOrUpdateDTO dto, Integer id);

    void deleteBanner(Integer id);

    PageResult<Banner> getBannerByPage(Integer count, Integer page);
}
