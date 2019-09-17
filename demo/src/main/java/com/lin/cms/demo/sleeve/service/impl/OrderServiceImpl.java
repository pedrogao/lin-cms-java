package com.lin.cms.demo.sleeve.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.common.mybatis.Page;
import com.lin.cms.demo.sleeve.enums.OrderStatus;
import com.lin.cms.demo.sleeve.mapper.OrderMapper;
import com.lin.cms.demo.sleeve.model.Order;
import com.lin.cms.demo.sleeve.model.OrderExpireDO;
import com.lin.cms.demo.sleeve.model.OrderParsedDO;
import com.lin.cms.demo.sleeve.service.IOrderService;
import com.lin.cms.demo.utils.DateDuration;
import com.lin.cms.exception.Forbidden;
import com.lin.cms.exception.NotFound;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author pedro
 * @since 2019-07-23
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Override
    public PageResult<OrderExpireDO> getOrderByPage(Long count, Long page) {
        Page pager = new Page(page, count);
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        IPage<Order> iPage  = this.getBaseMapper().selectPage(pager, wrapper);
        // IPage<Order> iPage = this.getBaseMapper().getOrderByPage(pager);
        List<Order> orders = iPage.getRecords();
        List<OrderExpireDO> orderExpires = new ArrayList<>();
        orders.forEach(order -> {
            Date createTime = order.getCreateTime();
            Date expireTime = DateDuration.addHourOfDate(createTime, 1);
            // 如果没有过期
            OrderExpireDO expireDO = new OrderExpireDO();
            BeanUtils.copyProperties(order, expireDO);
            expireDO.setExpired(expireTime.before(new Date()));
            orderExpires.add(expireDO);
        });
        return PageResult.genPageResult(iPage.getTotal(), orderExpires, page, count);
    }

    @Override
    public OrderParsedDO getParsedById(Long id) {
        Order order = this.getBaseMapper().selectById(id);
        if (order == null) {
            throw new NotFound("订单不存在");
        }
        OrderParsedDO orderParsedDO = new OrderParsedDO(order);
        return orderParsedDO;
    }

    @Override
    public void changeOrderStatus(Long id, Integer status) {
        Order order = this.getBaseMapper().selectById(id);
        if (order == null) {
            throw new NotFound("订单不存在");
        }
        // 检查订单状态
        if (order.getStatus() != OrderStatus.PAID.getValue() && order.getStatus() != OrderStatus.DELIVERED.getValue()) {
            throw new Forbidden("订单状态不可更改");
        }
        if (order.getStatus() == OrderStatus.PAID.getValue()) {
            if (status != OrderStatus.DELIVERED.getValue()) {
                throw new Forbidden("当前订单状态为已支付状态，只能向已发货转换");
            }
            this.getBaseMapper().changeOrderStatus(status, id);
        }
        if (order.getStatus() == OrderStatus.DELIVERED.getValue()) {
            if (status != OrderStatus.FINISHED.getValue()) {
                throw new Forbidden("当前订单状态为已发货状态，只能向已完成转换");
            }
            this.getBaseMapper().changeOrderStatus(status, id);
        }
    }
}
