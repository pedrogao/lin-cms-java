package com.lin.cms.demo.v2.service;

import com.lin.cms.response.PageResult;
import com.lin.cms.demo.v2.model.LogDO;
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

    List<String> getUserNames(Long page, Long count);

    void createLog(String message, String permission, Long userId,
                      String username, String method, String path,
                      Integer status);
}
