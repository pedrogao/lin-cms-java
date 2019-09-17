package com.lin.cms.demo.sleeve.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.sleeve.model.Order;
import com.lin.cms.demo.sleeve.model.OrderExpireDO;
import com.lin.cms.demo.sleeve.model.OrderParsedDO;

/**
 * @author pedro
 * @since 2019-07-23
 */
public interface IOrderService extends IService<Order> {

    PageResult<OrderExpireDO> getOrderByPage(Long count, Long page);

    OrderParsedDO getParsedById(Long id);

    void changeOrderStatus(Long id, Integer status);
}
