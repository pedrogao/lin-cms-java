package com.lin.cms.demo.sleeve.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.sleeve.dto.CouponCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.model.Coupon;
import com.lin.cms.demo.sleeve.model.CouponTemplate;
import com.lin.cms.demo.sleeve.model.SuggestionDO;

import java.util.List;

/**
 * @author pedro
 * @since 2019-08-09
 */
public interface ICouponService extends IService<Coupon> {

    void createCoupon(CouponCreateOrUpdateDTO dto);

    void updateCoupon(CouponCreateOrUpdateDTO dto, Long id);

    void deleteCoupon(Long id);

    PageResult<Coupon> getCouponByPage(Long count, Long page);

    List<CouponTemplate> getTemplates();

    List<SuggestionDO> getSuggestions(Long id);
}
