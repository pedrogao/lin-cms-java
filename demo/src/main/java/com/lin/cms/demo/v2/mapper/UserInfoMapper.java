package com.lin.cms.demo.v2.mapper;

import com.lin.cms.demo.v2.model.UserInfoDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author pedro
 * @since 2019-11-30
 */
@Service("userInfoMapper2")
public interface UserInfoMapper extends BaseMapper<UserInfoDO> {

}
