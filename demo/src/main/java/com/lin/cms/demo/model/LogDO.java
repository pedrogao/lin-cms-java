package com.lin.cms.demo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("lin_log")
@Data
public class LogDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String message;

    private Long userId;

    private String userName;

    private Integer statusCode;

    private String method;

    private String path;

    private String authority;

    private Date time;
}