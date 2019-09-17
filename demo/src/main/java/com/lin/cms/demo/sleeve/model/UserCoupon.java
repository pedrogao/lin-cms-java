package com.lin.cms.demo.sleeve.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author pedro
 * @since 2019-09-11
 */
@Data
public class UserCoupon implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long userId;

    private Long couponId;

    /**
     * 1:未使用，2：已使用， 3：已过期
     */
    private Integer status;

    private Date createTime;

    private Long orderId;

    private Date updateTime;
}
