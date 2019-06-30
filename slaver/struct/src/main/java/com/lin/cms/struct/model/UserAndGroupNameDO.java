package com.lin.cms.struct.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.Date;

@Data
public class UserAndGroupNameDO {
    private Integer id;

    private String nickname;

    /**
     * 分组名
     */
    private String groupName;

    /**
     * 头像url
     */
    private String avatar;

    private Byte admin;

    private Byte active;

    private String email;

    private Integer groupId;

    @JSONField(serialize = false)
    private String password;

    private Date createTime;

    private Date updateTime;

    @JSONField(serialize = false)
    private Date deleteTime;
}
