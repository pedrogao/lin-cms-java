package com.lin.cms.demo.sleeve.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.cms.demo.sleeve.model.ActivityCoupon;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author pedro
 * @since 2019-08-09
 */
public interface ActivityCouponMapper extends BaseMapper<ActivityCoupon> {
    List<Long> getCouponIds(@Param("activityId") Long activityId);
}
