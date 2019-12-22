package com.lin.cms.demo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lin.cms.demo.model.LogDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

/**
 * @author pedro
 * @since 2019-11-30
 */
public interface LogService extends IService<LogDO> {

    IPage<LogDO> getLogs(Long page, Long count, String name, Date start, Date end);

    IPage<LogDO> searchLogs(Long page, Long count, String name, String keyword, Date start, Date end);

    IPage<String> getUserNames(Long page, Long count);

    boolean createLog(String message, String permission, Long userId,
                      String username, String method, String path,
                      Integer status);
}
