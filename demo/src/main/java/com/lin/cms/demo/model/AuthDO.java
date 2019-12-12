package com.lin.cms.demo.model;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@TableName("lin_auth")
@Data
public class AuthDO {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private Long groupId;

    private String auth;

    private String module;
}