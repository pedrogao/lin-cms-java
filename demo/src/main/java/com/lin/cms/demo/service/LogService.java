package com.lin.cms.demo.service;

import com.lin.cms.demo.service.base.Service;
import com.lin.cms.core.result.PageResult;
import com.lin.cms.demo.model.LogPO;

import java.util.Date;
import java.util.List;


/**
 * Created by lin on 2019/06/14.
 * License MIT
 */
public interface LogService extends Service<LogPO> {

    PageResult getLogs(Integer page, Integer count, String name, Date start, Date end);

    PageResult searchLogs(Integer page, Integer count, String name, String keyword, Date start, Date end);

    List<String> getUserNames(Integer page, Integer count);
}
