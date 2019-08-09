package com.lin.cms.demo.sleeve.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @author pedro
 * @since 2019-08-09
 */
@Data
public class ActivitySpu implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long activityId;

    private Long spuId;

    private Integer participation;
}
