package com.lin.cms.demo.sleeve.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;
import com.lin.cms.demo.utils.DateDuration;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class OrderParsedDO {

    private Long id;

    private String orderNo;

    /**
     * user表外键
     */
    private Long userId;

    private BigDecimal totalPrice;

    private Integer totalCount;

    private String snapImg;

    private String snapTitle;

    private List<Map> snapItems;

    private Map snapAddress;

    private String prepayId;

    private BigDecimal finalTotalPrice;

    private Integer status;

    private boolean expired;

    @JSONField(serialize = false)
    private Date createTime;

    @JSONField(serialize = false)
    private Date deleteTime;

    @JSONField(serialize = false)
    private Date updateTime;

    public OrderParsedDO(Order order) {
        BeanUtils.copyProperties(order, this);
        List<Map> snapItems = JSON.parseArray(order.getSnapItems(), Map.class);
        Map snapAddress = JSON.parseObject(order.getSnapAddress(), Map.class);
        this.snapItems = snapItems;
        this.snapAddress = snapAddress;
        Date createTime = order.getCreateTime();
        Date expireTime = DateDuration.addHourOfDate(createTime, 1);
        // 如果没有过期
        this.setExpired(expireTime.before(new Date()));
    }

    public OrderParsedDO() {
    }
}
