package com.lin.cms.demo.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.amdelamar.jhash.Hash;
import com.amdelamar.jhash.algorithms.Type;
import com.amdelamar.jhash.exception.InvalidHashException;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lin.cms.core.enums.UserActive;
import com.lin.cms.core.enums.UserAdmin;
import lombok.Data;

import java.util.Date;

@TableName("lin_user")
@Data
public class UserDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String nickname;

    /**
     * 头像url
     */
    private String avatar;

    private Byte admin;

    private Byte active;

    private String email;

    private Long groupId;

    @JSONField(serialize = false)
    private String password;

    private Date createTime;

    private Date updateTime;

    @JSONField(serialize = false)
    @TableLogic
    private Date deleteTime;


    public boolean checkAdmin() {
        return this.admin.intValue() == UserAdmin.ADMIN.getValue();
    }

    public boolean checkActive() {
        return this.active.intValue() == UserActive.ACTIVE.getValue();
    }

    /**
     * 设置密文密码
     *
     * @param password 原始密码
     */
    public void setPasswordEncrypt(String password) {
        char[] chars = password.toCharArray();
        this.password = Hash.password(chars).algorithm(Type.PBKDF2_SHA256).create();
    }

    /**
     * 验证加密密码
     *
     * @param password 密文密码
     * @return valid
     */
    public boolean verify(String password) {
        char[] chars = password.toCharArray();
        try {
            return Hash.password(chars).verify(this.password);
        } catch (InvalidHashException e) {
            return false;
        }
    }
}