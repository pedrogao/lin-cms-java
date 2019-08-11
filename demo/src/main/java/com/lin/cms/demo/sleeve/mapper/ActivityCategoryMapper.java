package com.lin.cms.demo.sleeve.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lin.cms.demo.sleeve.model.ActivityCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author pedro
 * @since 2019-08-09
 */
public interface ActivityCategoryMapper extends BaseMapper<ActivityCategory> {
    List<Long> getCategoryIds(@Param("activityId") Long activityId);
}
