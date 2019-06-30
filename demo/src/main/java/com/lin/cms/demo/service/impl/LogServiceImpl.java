package com.lin.cms.demo.service.impl;

import com.github.pagehelper.PageHelper;
import com.lin.cms.core.annotation.RouteMeta;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.mapper.LogMapper;
import com.lin.cms.demo.model.UserDO;
import com.lin.cms.demo.service.LogService;
import com.lin.cms.demo.service.base.AbstractService;
import com.lin.cms.demo.model.LogDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;


/**
 * Created by lin on 2019/06/14.
 */
@Service
public class LogServiceImpl extends AbstractService<LogDO> implements LogService {
    @Autowired
    private LogMapper logMapper;

    @Override
    public PageResult getLogs(Integer page, Integer count, String name, Date start, Date end) {
        PageHelper.startPage(page + 1, count);
        List<LogDO> logs = logMapper.findLogsByUsernameAndRange(name, start, end);
        Integer total = logMapper.countLogsByUsernameAndRange(name, start, end);
        return PageResult.genPageResult(total, logs);
    }

    @Override
    public PageResult searchLogs(Integer page, Integer count, String name, String keyword, Date start, Date end) {
        PageHelper.startPage(page + 1, count);
        List<LogDO> logs = logMapper.searchLogsByUsernameAndKeywordAndRange(name, keyword, start, end);
        Integer total = logMapper.countLogsByUsernameAndKeywordAndRange(name, keyword, start, end);
        return PageResult.genPageResult(total, logs);
    }

    @Override
    public List<String> getUserNames(Integer page, Integer count) {
        PageHelper.startPage(page + 1, count);
        List<String> userNames = logMapper.getUserNames();
        return userNames;
    }

    @Override
    public void createOneLog(String message, RouteMeta meta, UserDO user, HttpServletRequest request, HttpServletResponse response) {
        //authority: auth
        LogDO record = new LogDO();
        record.setMessage(message);
        record.setUserId(user.getId());
        record.setUserName(user.getNickname());
        record.setStatusCode(response.getStatus());
        record.setMethod(request.getMethod());
        record.setPath(request.getServletPath());
        if (meta != null) {
            record.setAuthority(meta.auth());
        }
        logMapper.insertSelective(record);
    }
}
