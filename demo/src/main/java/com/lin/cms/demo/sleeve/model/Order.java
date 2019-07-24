package com.lin.cms.demo.sleeve.model;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author pedro
 * @since 2019-07-23
 */
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer orderNo;

    /**
     * user表外键
     */
    private Integer uid;

    private BigDecimal totalPrice;

    private Integer skuCount;

    @JSONField(serialize = false)
    private Date createTime;

    @JSONField(serialize = false)
    private Date deleteTime;

    @JSONField(serialize = false)
    private Date updateTime;

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }
    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }
    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
    public Integer getSkuCount() {
        return skuCount;
    }

    public void setSkuCount(Integer skuCount) {
        this.skuCount = skuCount;
    }
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Order{" +
            "orderNo=" + orderNo +
            ", uid=" + uid +
            ", totalPrice=" + totalPrice +
            ", skuCount=" + skuCount +
            ", createTime=" + createTime +
            ", deleteTime=" + deleteTime +
            ", updateTime=" + updateTime +
        "}";
    }
}
