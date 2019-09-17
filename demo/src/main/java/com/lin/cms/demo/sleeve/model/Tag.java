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
 * @since 2019-08-09
 */
@Data
public class Tag implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 中文限制6个，英文限制12个，由逻辑层控制
     */
    private String title;

    private String description;

    private Integer highlight;

    private Integer type;

    @JSONField(serialize = false)
    private Date updateTime;

    @TableLogic
    @JSONField(serialize = false)
    private Date deleteTime;

    @JSONField(serialize = false)
    private Date createTime;
}
