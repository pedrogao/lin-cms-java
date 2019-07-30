package com.lin.cms.demo.sleeve.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lin.cms.demo.common.mybatis.Page;
import com.lin.cms.demo.sleeve.model.Order;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author pedro
 * @since 2019-07-23
 */
public interface OrderMapper extends BaseMapper<Order> {
    IPage<Order> getOrderByPage(Page page);
}
