package com.lin.cms.demo.sleeve.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author pedro
 * @since 2019-09-11
 */
@Data
public class SaleExplain implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Integer fixed;

    private String text;

    private Long spuId;

    private Integer index;

    private Long replaceId;

    @JSONField(serialize = false)
    private Date createTime;

    @JSONField(serialize = false)
    @TableLogic
    private Date deleteTime;

    @JSONField(serialize = false)
    private Date updateTime;
}
