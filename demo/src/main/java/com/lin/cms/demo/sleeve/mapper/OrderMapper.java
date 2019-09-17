package com.lin.cms.demo.sleeve.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lin.cms.demo.common.mybatis.Page;
import com.lin.cms.demo.sleeve.model.Order;
import org.apache.ibatis.annotations.Param;

/**
 * @author pedro
 * @since 2019-07-23
 */
public interface OrderMapper extends BaseMapper<Order> {
    IPage<Order> getOrderByPage(Page page);

    int changeOrderStatus(@Param("status") Integer status, @Param("id") Long id);
}
