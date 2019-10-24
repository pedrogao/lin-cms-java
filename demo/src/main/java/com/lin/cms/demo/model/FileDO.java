package com.lin.cms.demo.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("lin_file")
@Data
public class FileDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String path;

    /**
     * 1 local(本地)，其他表示其他地方
     */
    private Byte type;

    private String name;

    private String extension;

    private Integer size;

    /**
     * 图片md5值，防止上传重复图片
     */
    private String md5;
}