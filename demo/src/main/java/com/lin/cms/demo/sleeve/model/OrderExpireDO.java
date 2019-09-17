package com.lin.cms.demo.sleeve.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderExpireDO {

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

    private String snapItems;

    private String snapAddress;

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
}
