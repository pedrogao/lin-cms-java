package com.lin.cms.demo.vo;

import com.lin.cms.demo.model.UserDO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class UserAuthsVO {
    private Integer id;

    private String nickname;

    private String avatar;

    private Byte admin;

    private Byte active;

    private String email;

    private Integer groupId;

    private Date createTime;

    private Date updateTime;

    private List auths;

    public UserAuthsVO() {
    }

    public UserAuthsVO(UserDO userDO, List auths) {
        BeanUtils.copyProperties(userDO, this);
        this.auths = auths;
    }

    public UserAuthsVO(UserDO userDO) {
        BeanUtils.copyProperties(userDO, this);
    }
}
