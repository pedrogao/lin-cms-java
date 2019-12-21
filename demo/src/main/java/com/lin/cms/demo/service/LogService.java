package com.lin.cms.demo.service;

import com.lin.cms.demo.vo.PageResult;
import com.lin.cms.demo.model.LogDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
 * @author pedro
 * @since 2019-11-30
 */
public interface LogService extends IService<LogDO> {

    PageResult getLogs(Long page, Long count, String name, Date start, Date end);

    PageResult searchLogs(Long page, Long count, String name, String keyword, Date start, Date end);

    PageResult getUserNames(Long page, Long count);

    boolean createLog(String message, String permission, Long userId,
                      String username, String method, String path,
                      Integer status);
}
