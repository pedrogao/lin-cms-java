package com.lin.cms.demo.v2.mapper;

import com.lin.cms.demo.v2.model.UserDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Service;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author pedro
 * @since 2019-12-02
 */
@Service("userMapper2")
public interface UserMapper extends BaseMapper<UserDO> {

}
