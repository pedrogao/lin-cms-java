package com.lin.cms.demo.v2.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author pedro
 * @since 2019-11-30
 */
@Data
@Builder
@TableName("lin_group")
@NoArgsConstructor
@AllArgsConstructor
public class GroupDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 分组名称，例如：搬砖者
     */
    private String name;

    /**
     * 分组信息：例如：搬砖的人
     */
    private String info;

    @JSONField(serialize = false)
    @JsonIgnore
    private Date createTime;

    @JSONField(serialize = false)
    @JsonIgnore
    private Date updateTime;

    @JSONField(serialize = false)
    @JsonIgnore
    @TableLogic
    private Date deleteTime;
}
