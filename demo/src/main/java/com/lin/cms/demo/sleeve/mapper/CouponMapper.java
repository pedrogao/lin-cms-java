package com.lin.cms.demo.sleeve.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.cms.demo.sleeve.model.Coupon;
import com.lin.cms.demo.sleeve.model.SuggestionDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author pedro
 * @since 2019-08-09
 */
public interface CouponMapper extends BaseMapper<Coupon> {

    List<SuggestionDO> getSuggestions(@Param("id") Long id);
}
