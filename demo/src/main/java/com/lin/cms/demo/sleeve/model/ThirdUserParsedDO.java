package com.lin.cms.demo.sleeve.model;

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.Map;

@Data
public class ThirdUserParsedDO {
    private Long id;

    private String openid;

    private String nickname;

    private String email;

    private String mobile;

    private Map wxProfile;

    public ThirdUserParsedDO(ThirdUser user) {
        BeanUtils.copyProperties(user, this);
        Map object = JSON.parseObject(user.getWxProfile(), Map.class);
        this.wxProfile = object;
    }
}
