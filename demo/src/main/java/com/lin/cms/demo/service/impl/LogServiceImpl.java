package com.lin.cms.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.common.mybatis.Page;
import com.lin.cms.demo.mapper.LogMapper;
import com.lin.cms.demo.service.LogService;
import com.lin.cms.demo.model.LogDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * Created by lin on 2019/06/14.
 */
@Service
public class LogServiceImpl implements LogService {
    @Autowired
    private LogMapper logMapper;

    @Override
    public PageResult getLogs(Integer page, Integer count, String name, Date start, Date end) {
        Page<LogDO> pager = new Page<>(page, count);
        IPage<LogDO> iPage = logMapper.findLogsByUsernameAndRange(pager, name, start, end);
        return PageResult.genPageResult(iPage.getTotal(), iPage.getRecords());
    }

    @Override
    public PageResult searchLogs(Integer page, Integer count, String name, String keyword, Date start, Date end) {
        Page<LogDO> pager = new Page<>(page, count);
        IPage<LogDO> iPage = logMapper.searchLogsByUsernameAndKeywordAndRange(pager, name, keyword, start, end);
        return PageResult.genPageResult(iPage.getTotal(), iPage.getRecords());
    }

    @Override
    public List<String> getUserNames(Integer page, Integer count) {
        Page<LogDO> pager = new Page<>(page, count);
        IPage<String> iPage = logMapper.getUserNames(pager);
        return iPage.getRecords();
    }

    @Override
    public void createOneLog(String message, String authority, Integer userId,
                             String userNickname, String method, String path,
                             Integer status) {
        //authority: auth
        LogDO record = new LogDO();
        record.setMessage(message);
        record.setUserId(userId);
        record.setUserName(userNickname);
        record.setStatusCode(status);
        record.setMethod(method);
        record.setPath(path);
        if (authority != null) {
            record.setAuthority(authority);
        }
        logMapper.insert(record);
    }
}
