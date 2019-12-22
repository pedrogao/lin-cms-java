package com.lin.cms.demo.service;

import com.lin.cms.demo.vo.PageResultVO;
import com.lin.cms.demo.model.LogDO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;

/**
 * @author pedro
 * @since 2019-11-30
 */
public interface LogService extends IService<LogDO> {

    PageResultVO getLogs(Long page, Long count, String name, Date start, Date end);

    PageResultVO searchLogs(Long page, Long count, String name, String keyword, Date start, Date end);

    PageResultVO getUserNames(Long page, Long count);

    boolean createLog(String message, String permission, Long userId,
                      String username, String method, String path,
                      Integer status);
}
