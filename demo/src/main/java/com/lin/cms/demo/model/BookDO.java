package com.lin.cms.demo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName("book")
@Data
public class BookDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String title;

    private String author;

    private String summary;

    private String image;

    private Date createTime;

    private Date updateTime;

    private Date deleteTime;
}