package com.lin.cms.demo.model;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserAndGroupNameDO {
    private Long id;

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
