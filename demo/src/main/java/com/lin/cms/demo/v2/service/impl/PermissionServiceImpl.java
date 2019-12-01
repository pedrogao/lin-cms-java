package com.lin.cms.demo.v2.service.impl;

import com.lin.cms.demo.v2.model.PermissionDO;
import com.lin.cms.demo.v2.mapper.PermissionMapper;
import com.lin.cms.demo.v2.service.PermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author pedro
 * @since 2019-11-30
 */
@Service("permissionServiceImpl-v2")
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, PermissionDO> implements PermissionService {

}
