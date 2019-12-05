package com.lin.cms.demo.v2.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lin.cms.demo.common.mybatis.Page;
import com.lin.cms.demo.v2.model.LogDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author pedro
 * @since 2019-11-30
 */
@Service("logMapper2")
public interface LogMapper extends BaseMapper<LogDO> {

    IPage<LogDO> findLogsByUsernameAndRange(Page<LogDO> pager, String name, Date start, Date end);

    IPage<LogDO> searchLogsByUsernameAndKeywordAndRange(Page<LogDO> pager, String name, String keyword, Date start, Date end);

    IPage<String> getUserNames(Page<LogDO> pager);
}
