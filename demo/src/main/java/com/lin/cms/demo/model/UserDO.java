package com.lin.cms.demo.model;

import com.alibaba.fastjson.annotation.JSONField;
import com.amdelamar.jhash.Hash;
import com.amdelamar.jhash.algorithms.Type;
import com.amdelamar.jhash.exception.InvalidHashException;
import com.lin.cms.core.enums.UserActive;
import com.lin.cms.core.enums.UserAdmin;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "lin_user")
@Entity(name = "lin_user")
@DynamicInsert
@DynamicUpdate
public class UserDO {
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

    @Column(name = "group_id")
    private Integer groupId;

    @JSONField(serialize = false)
    private String password;

    @Column(name = "create_time")
    @CreationTimestamp
    private Date createTime;

    @Column(name = "update_time")
    @UpdateTimestamp
    private Date updateTime;

    @Column(name = "delete_time")
    @JSONField(serialize = false)
    private Date deleteTime;


    public boolean ifIsAdmin() {
        return this.admin.intValue() == UserAdmin.ADMIN.getValue();
    }

    public boolean ifIsActive() {
        return this.active.intValue() == UserActive.ACTIVE.getValue();
    }

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return nickname
     */
    public String getNickname() {
        return nickname;
    }

    /**
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * 获取头像url
     *
     * @return avatar - 头像url
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * 设置头像url
     *
     * @param avatar 头像url
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * @return admin
     */
    public Byte getAdmin() {
        return admin;
    }

    /**
     * @param admin
     */
    public void setAdmin(Byte admin) {
        this.admin = admin;
    }

    /**
     * @return active
     */
    public Byte getActive() {
        return active;
    }

    /**
     * @param active
     */
    public void setActive(Byte active) {
        this.active = active;
    }

    /**
     * @return email
     */
    public String getEmail() {
        return email;
    }

    /**
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * @return group_id
     */
    public Integer getGroupId() {
        return groupId;
    }

    /**
     * @param groupId
     */
    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return delete_time
     */
    public Date getDeleteTime() {
        return deleteTime;
    }

    /**
     * @param deleteTime
     */
    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
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