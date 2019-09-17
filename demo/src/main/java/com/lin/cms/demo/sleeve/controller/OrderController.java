package com.lin.cms.demo.sleeve.controller;


import com.lin.cms.core.annotation.RouteMeta;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.core.result.Result;
import com.lin.cms.demo.sleeve.model.Order;
import com.lin.cms.demo.sleeve.model.OrderExpireDO;
import com.lin.cms.demo.sleeve.model.OrderParsedDO;
import com.lin.cms.demo.sleeve.service.IOrderService;
import com.lin.cms.exception.NotFound;
import com.lin.cms.utils.ResultGenerator;
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

    @GetMapping("/{id}/parsed")
    public OrderParsedDO getParsed(@PathVariable @Positive(message = "id必须为正整数") Long id) {
        OrderParsedDO order = orderService.getParsedById(id);
        if (order == null) {
            throw new NotFound("未找到相关的订单");
        }
        return order;
    }


    @GetMapping("/page")
    public PageResult<OrderExpireDO> page(@RequestParam(name = "count", required = false, defaultValue = "10")
                                          @Min(value = 1, message = "count必须为正整数") Long count,
                                          @RequestParam(name = "page", required = false, defaultValue = "0")
                                          @Min(value = 0, message = "page必须为整数，且大于等于0") Long page) {
        PageResult<OrderExpireDO> pageResult = orderService.getOrderByPage(count, page);
        return pageResult;
    }

    @RouteMeta(auth = "更新订单状态", module = "订单", mount = true)
    @PutMapping("/status")
    public Result changeOrderStatus(@RequestParam(name = "id")
                                    @Min(value = 1, message = "id必须为正整数") Long id,
                                    @RequestParam(name = "status")
                                    @Min(value = 0, message = "status必须为整数") Integer status) {
        orderService.changeOrderStatus(id, status);
        return ResultGenerator.genSuccessResult("更新状态成功");
    }
}
