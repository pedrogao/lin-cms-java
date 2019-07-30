package com.lin.cms.demo.sleeve.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.sleeve.model.Order;

/**
 * @author pedro
 * @since 2019-07-23
 */
public interface IOrderService extends IService<Order> {

    PageResult<Order> getOrderByPage(Long count, Long page);
}
