package com.lin.cms.demo.sleeve.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String orderNo;

    /**
     * user表外键
     */
    private Long uid;

    private BigDecimal totalPrice;

    private Integer skuCount;

    @JSONField(serialize = false)
    private Date createTime;

    @JSONField(serialize = false)
    @TableLogic
    private Date deleteTime;

    @JSONField(serialize = false)
    private Date updateTime;
}
