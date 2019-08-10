package com.lin.cms.demo.sleeve.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.common.mybatis.Page;
import com.lin.cms.demo.sleeve.dto.CouponCreateOrUpdateDTO;
import com.lin.cms.demo.sleeve.enums.CouponType;
import com.lin.cms.demo.sleeve.mapper.CouponMapper;
import com.lin.cms.demo.sleeve.mapper.CouponTemplateMapper;
import com.lin.cms.demo.sleeve.model.Coupon;
import com.lin.cms.demo.sleeve.model.CouponTemplate;
import com.lin.cms.demo.sleeve.service.ICouponService;
import com.lin.cms.exception.NotFound;
import com.lin.cms.exception.Parameter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author pedro
 * @since 2019-08-09
 */
@Service
public class CouponServiceImpl extends ServiceImpl<CouponMapper, Coupon> implements ICouponService {

    @Autowired
    private CouponTemplateMapper couponTemplateMapper;

    @Override
    public void createCoupon(CouponCreateOrUpdateDTO dto) {
        boolean ok = checkCouponType(dto);
        if (!ok) {
            throw new Parameter("优惠卷数据不符合优惠卷类型");
        }
        Coupon coupon = new Coupon();
        BeanUtils.copyProperties(dto, coupon);
        this.save(coupon);
    }

    @Override
    public void updateCoupon(CouponCreateOrUpdateDTO dto, Long id) {
        Coupon exist = this.getById(id);
        if (exist == null) {
            throw new NotFound("未找到相关的优惠卷");
        }
        boolean ok = checkCouponType(dto);
        if (!ok) {
            throw new Parameter("优惠卷数据不符合优惠卷类型");
        }
        BeanUtils.copyProperties(dto, exist);
        this.updateById(exist);
    }

    @Override
    public void deleteCoupon(Long id) {
        Coupon exist = this.getById(id);
        if (exist == null) {
            throw new NotFound("未找到相关的优惠卷");
        }
        this.getBaseMapper().deleteById(id);
    }

    @Override
    public PageResult<Coupon> getCouponByPage(Long count, Long page) {
        Page pager = new Page(page, count);
        IPage<Coupon> iPage = this.getBaseMapper().selectPage(pager, null);
        List<Coupon> categories = iPage.getRecords();
        return PageResult.genPageResult(iPage.getTotal(), categories);
    }

    @Override
    public List<CouponTemplate> getTemplates() {
        return couponTemplateMapper.selectList(null);
    }

    /**
     * 校验优惠卷数据是否满足优惠卷类型
     */
    private boolean checkCouponType(CouponCreateOrUpdateDTO dto) {
        if (dto.getType() == CouponType.FULL_MONEY_CUT.getValue()) {
            return (dto.getFullMoney() != null && dto.getMinus() != null);
        } else if (dto.getType() == CouponType.DISCOUNT.getValue()) {
            return dto.getDiscount() != null;
        } else if (dto.getType() == CouponType.ALL.getValue()) {
            return true;
        } else if (dto.getType() == CouponType.FULL_MONEY_DISCOUNT.getValue()) {
            return (dto.getFullMoney() != null && dto.getDiscount() != null);
        } else {
            return false;
        }
    }
}
