package com.lin.cms.demo.sleeve.controller;


import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.sleeve.model.Order;
import com.lin.cms.demo.sleeve.service.IOrderService;
import com.lin.cms.exception.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

/**
 * @author pedro
 * @since 2019-07-23
 */
@RestController
@RequestMapping("/sleeve/order")
@Validated
public class OrderController {

    @Autowired
    private IOrderService orderService;

    @GetMapping("/{id}")
    public Order get(@PathVariable @Positive(message = "id必须为正整数") Long id) {
        Order order = orderService.getById(id);
        if (order == null) {
            throw new NotFound("未找到相关的订单");
        }
        return order;
    }


    @GetMapping("/page")
    public PageResult<Order> page(@RequestParam(name = "count", required = false, defaultValue = "10")
                                   @Min(value = 1, message = "count必须为正整数") Long count,
                                   @RequestParam(name = "page", required = false, defaultValue = "0")
                                   @Min(value = 0, message = "page必须为整数，且大于等于0") Long page) {
        PageResult<Order> pageResult = orderService.getOrderByPage(count, page);
        if (pageResult.getTotalNums() == 0) {
            throw new NotFound("未找到相关的订单");
        }
        return pageResult;
    }
}
