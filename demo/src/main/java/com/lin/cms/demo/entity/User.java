package com.lin.cms.demo.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.lin.cms.core.enums.UserActive;
import com.lin.cms.core.enums.UserAdmin;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

//@Entity(name = "lin_user")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nickname;

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

    @CreationTimestamp
    private Date createTime;

    @UpdateTimestamp
    private Date updateTime;

    @JSONField(serialize = false)
    private Date deleteTime;

    public boolean ifIsAdmin() {
        return this.admin.intValue() == UserAdmin.ADMIN.getValue();
    }

    public boolean ifIsActive() {
        return this.active.intValue() == UserActive.ACTIVE.getValue();
    }

}
