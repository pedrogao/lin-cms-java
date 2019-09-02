package com.lin.cms.plugins.poem.app;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@TableName(value = "poem")
@Data
public class PoemDO {
    private Integer id;

    private String title;

    private String author;

    private String dynasty;

    private String image;

    @TableLogic
    private Date deleteTime;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    private String content;
}