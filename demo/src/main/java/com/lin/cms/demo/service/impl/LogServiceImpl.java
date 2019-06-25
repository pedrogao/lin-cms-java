package com.lin.cms.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.lin.cms.demo.db.AbstractService;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.mapper.LogMapper;
import com.lin.cms.demo.model.LogPO;
import com.lin.cms.demo.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * Created by lin on 2019/06/14.
 */
@Service
public class LogServiceImpl extends AbstractService<LogPO> implements LogService {
    @Autowired
    private LogMapper logMapper;

    @Override
    public PageResult getLogs(Integer page, Integer count, String name, Date start, Date end) {
        PageHelper.startPage(page + 1, count);
        List<LogPO> logs = logMapper.findLogsByUsernameAndRange(name, start, end);
        Integer total = logMapper.CountLogsByUsernameAndRange(name, start, end);
        return PageResult.genPageResult(total, logs);
    }

    @Override
    public PageResult searchLogs(Integer page, Integer count, String name, String keyword, Date start, Date end) {
        PageHelper.startPage(page + 1, count);
        List<LogPO> logs = logMapper.searchLogsByUsernameAndKeywordAndRange(name, keyword, start, end);
        Integer total = logMapper.CountLogsByUsernameAndKeywordAndRange(name, keyword, start, end);
        return PageResult.genPageResult(total, logs);
    }

    @Override
    public List<String> getUserNames(Integer page, Integer count) {
        PageHelper.startPage(page + 1, count);
        List<String> userNames = logMapper.getUserNames();
        return userNames;
    }
}
