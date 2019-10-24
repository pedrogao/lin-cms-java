package com.lin.cms.demo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;


@TableName("lin_group")
@Data
public class GroupDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String name;

    private String info;
}