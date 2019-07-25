package com.lin.cms.demo.sleeve.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.common.mybatis.Page;
import com.lin.cms.demo.sleeve.mapper.OrderMapper;
import com.lin.cms.demo.sleeve.model.Order;
import com.lin.cms.demo.sleeve.service.IOrderService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pedro
 * @since 2019-07-23
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

    @Override
    public PageResult<Order> getOrderByPage(Long count, Long page) {
        Page pager = new Page(page, count);
        IPage<Order> iPage = this.getBaseMapper().selectPage(pager, null);
        List<Order> categories = iPage.getRecords();
        return PageResult.genPageResult(iPage.getTotal(), categories);
    }
}
