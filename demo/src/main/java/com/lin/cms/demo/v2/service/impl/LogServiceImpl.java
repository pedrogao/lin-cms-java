package com.lin.cms.demo.v2.service.impl;

import com.lin.cms.demo.v2.model.LogDO;
import com.lin.cms.demo.v2.mapper.LogMapper;
import com.lin.cms.demo.v2.service.LogService;
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
@Service("logServiceImpl-v2")
public class LogServiceImpl extends ServiceImpl<LogMapper, LogDO> implements LogService {

}
