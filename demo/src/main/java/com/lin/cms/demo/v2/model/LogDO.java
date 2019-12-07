package com.lin.cms.demo.v2.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("lin_log")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogDO implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String message;

    private Long userId;

    private String username;

    private Integer statusCode;

    private String method;

    private String path;

    private String permission;

    @JsonIgnore
    @JSONField(serialize = false)
    private Date createTime;

    @JsonIgnore
    @JSONField(serialize = false)
    private Date updateTime;

    @JSONField(serialize = false)
    @TableLogic
    private Date deleteTime;

}
