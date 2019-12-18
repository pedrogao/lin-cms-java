package com.lin.cms.demo.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lin.cms.demo.vo.PageResult;
import com.lin.cms.demo.common.mybatis.Page;
import com.lin.cms.demo.model.LogDO;
import com.lin.cms.demo.mapper.LogMapper;
import com.lin.cms.demo.service.LogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author pedro
 * @since 2019-11-30
 */
@Service("logServiceImpl-v2")
public class LogServiceImpl extends ServiceImpl<LogMapper, LogDO> implements LogService {

    @Override
    public PageResult getLogs(Long page, Long count, String name, Date start, Date end) {
        Page<LogDO> pager = new Page<>(page, count);
        IPage<LogDO> iPage = this.baseMapper.findLogsByUsernameAndRange(pager, name, start, end);
        return PageResult.genPageResult(iPage.getTotal(), iPage.getRecords(), page, count);
    }

    @Override
    public PageResult searchLogs(Long page, Long count, String name, String keyword, Date start, Date end) {
        Page<LogDO> pager = new Page<>(page, count);
        IPage<LogDO> iPage = this.baseMapper.searchLogsByUsernameAndKeywordAndRange(pager, name, keyword, start, end);
        return PageResult.genPageResult(iPage.getTotal(), iPage.getRecords(), page, count);
    }

    @Override
    public List<String> getUserNames(Long page, Long count) {
        Page<LogDO> pager = new Page<>(page, count);
        IPage<String> iPage = this.baseMapper.getUserNames(pager);
        return iPage.getRecords();
    }

    @Override
    public boolean createLog(String message, String permission, Long userId, String username, String method, String path, Integer status) {
        LogDO record = LogDO.builder()
                .message(message)
                .userId(userId)
                .username(username)
                .statusCode(status)
                .method(method)
                .path(path)
                .build();
        if (permission != null) {
            record.setPermission(permission);
        }
        return this.baseMapper.insert(record) > 0;
    }
}
